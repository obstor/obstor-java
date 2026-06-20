/*
 * MinIO Java SDK for Amazon S3 Compatible Cloud Storage,
 * (C) 2015-2021 MinIO, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import net.obstor.Checksum;
import net.obstor.Http;
import net.obstor.ServerSideEncryption;
import net.obstor.errors.ErrorResponseException;
import net.obstor.errors.ObstorException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.crypto.KeyGenerator;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import org.junit.jupiter.api.Assertions;

@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = "THROWS_METHOD_THROWS_CLAUSE_BASIC_EXCEPTION")
public class TestArgs {
  public static final String OS = System.getProperty("os.name").toLowerCase(Locale.US);
  public static final String OBSTOR_BINARY = OS.contains("windows") ? "obstor.exe" : "obstor";
  public static final String PASS = "PASS";
  public static final String FAILED = "FAIL";
  public static final String IGNORED = "NA";
  public static final int KB = 1024;
  public static final int MB = 1024 * 1024;
  public static final Random RANDOM = new Random(new SecureRandom().nextLong());
  public static final String CUSTOM_CONTENT_TYPE = "application/javascript";
  public static final ServerSideEncryption SSE_S3 = new ServerSideEncryption.S3();
  public static final ServerSideEncryption.CustomerKey SSE_C;
  public static final boolean TESTS_ENV;
  public static final boolean IS_QUICK_TEST;
  public static final boolean IS_RUN_ON_FAIL;
  public static final Path DATA_FILE_1KB;
  public static final Path DATA_FILE_6MB;
  public static final String REPLICATION_SRC_BUCKET;
  public static final String REPLICATION_ROLE;
  public static final String REPLICATION_BUCKET_ARN;

  static {
    try {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(256);
      SSE_C = new ServerSideEncryption.CustomerKey(keyGen.generateKey());
    } catch (NoSuchAlgorithmException | ObstorException e) {
      throw new IllegalStateException(e);
    }

    String testsMode = System.getenv("TESTS_MODE");
    String dataDir = System.getenv("TESTS_DATA_DIR");
    TESTS_ENV = testsMode != null;
    IS_QUICK_TEST = TESTS_ENV && !"full".equals(testsMode);
    IS_RUN_ON_FAIL = TESTS_ENV && "1".equals(System.getenv("RUN_ON_FAIL"));
    DATA_FILE_1KB =
        (TESTS_ENV && dataDir != null && !dataDir.isEmpty())
            ? Paths.get(dataDir, "datafile-1-kB")
            : null;
    DATA_FILE_6MB =
        (TESTS_ENV && dataDir != null && !dataDir.isEmpty())
            ? Paths.get(dataDir, "datafile-6-MB")
            : null;
    REPLICATION_SRC_BUCKET = System.getenv("OBSTOR_JAVA_TEST_REPLICATION_SRC_BUCKET");
    REPLICATION_ROLE = System.getenv("OBSTOR_JAVA_TEST_REPLICATION_ROLE");
    REPLICATION_BUCKET_ARN = System.getenv("OBSTOR_JAVA_TEST_REPLICATION_BUCKET_ARN");
  }

  public boolean automated;
  public String endpoint;
  public String endpointTLS;
  public String accessKey;
  public String secretKey;
  public String region;
  public boolean isSecureEndpoint = false;
  public String sqsArn = null;
  public ServerSideEncryption sseKms = null;

  public TestArgs(TestArgs args) {
    this.automated = args.automated;
    this.endpoint = args.endpoint;
    this.endpointTLS = args.endpointTLS;
    this.accessKey = args.accessKey;
    this.secretKey = args.secretKey;
    this.region = args.region;
    this.isSecureEndpoint = args.isSecureEndpoint;
    this.sqsArn = args.sqsArn;
    this.sseKms = args.sseKms;
  }

  public TestArgs(String endpoint, String accessKey, String secretKey, String region)
      throws ObstorException {
    this.automated = endpoint == null;

    String kmsKeyName = "my-obstor-key";
    if (endpoint == null) {
      this.endpoint = "http://localhost:9000";
      this.endpointTLS = "https://localhost:9001";
      this.accessKey = "obstor";
      this.secretKey = "obstor123";
      this.region = "us-east-1";
      this.sqsArn = "arn:obstor:sqs::obstorjavatest:webhook";
    } else {
      if ((kmsKeyName = System.getenv("OBSTOR_JAVA_TEST_KMS_KEY_NAME")) == null) {
        kmsKeyName = System.getenv("TESTS_KEY_ID");
      }
      this.sqsArn = System.getenv("OBSTOR_JAVA_TEST_SQS_ARN");
      this.endpoint = endpoint;
      this.accessKey = accessKey;
      this.secretKey = secretKey;
      this.region = region;
    }
    this.isSecureEndpoint = this.endpoint.toLowerCase(Locale.US).contains("https://");

    if (kmsKeyName != null) {
      Map<String, String> myContext = new HashMap<>();
      myContext.put("key1", "value1");
      this.sseKms = new ServerSideEncryption.KMS(kmsKeyName, myContext);
    }
  }

  public static OkHttpClient newHttpClient() {
    try {
      return Http.disableCertCheck(Http.newDefaultClient());
    } catch (ObstorException e) {
      throw new IllegalStateException(e);
    }
  }

  /** Do no-op. */
  public static void ignore(Object... args) {}

  /** Create given sized file and returns its name. */
  public static String createFile(int size) throws IOException {
    String filename = getRandomName();

    try (OutputStream os = Files.newOutputStream(Paths.get(filename), CREATE, APPEND)) {
      int totalBytesWritten = 0;
      int bytesToWrite = 0;
      byte[] buf = new byte[1 * MB];
      while (totalBytesWritten < size) {
        RANDOM.nextBytes(buf);
        bytesToWrite = size - totalBytesWritten;
        if (bytesToWrite > buf.length) bytesToWrite = buf.length;
        os.write(buf, 0, bytesToWrite);
        totalBytesWritten += bytesToWrite;
      }
    }

    return filename;
  }

  /** Create 1 KB temporary file. */
  public static String createFile1Kb() throws IOException {
    if (TESTS_ENV) {
      String filename = getRandomName();
      Files.createSymbolicLink(Paths.get(filename).toAbsolutePath(), DATA_FILE_1KB);
      return filename;
    }

    return createFile(1 * KB);
  }

  /** Create 6 MB temporary file. */
  public static String createFile6Mb() throws IOException {
    if (TESTS_ENV) {
      String filename = getRandomName();
      Files.createSymbolicLink(Paths.get(filename).toAbsolutePath(), DATA_FILE_6MB);
      return filename;
    }

    return createFile(6 * MB);
  }

  /** Generate random name. */
  public static String getRandomName() {
    return "obstor-java-test-" + new BigInteger(32, RANDOM).toString(32);
  }

  /** Returns byte array contains all data in given InputStream. */
  public static byte[] readAllBytes(InputStream is) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    int n;
    byte[] data = new byte[16384];
    while ((n = is.read(data, 0, data.length)) != -1) buffer.write(data, 0, n);
    return buffer.toByteArray();
  }

  /** Prints a success log entry in JSON format. */
  public static void testsSuccessLog(String function, String args, long startTime) {
    if (TESTS_ENV) {
      System.out.println(
          new TestsLogger(
              function, args, System.currentTimeMillis() - startTime, PASS, null, null, null));
    }
  }

  /** Prints a failure log entry in JSON format. */
  public static void testsFailedLog(
      String function, String args, long startTime, String message, String error) {
    if (TESTS_ENV) {
      System.out.println(
          new TestsLogger(
              function,
              args,
              System.currentTimeMillis() - startTime,
              FAILED,
              null,
              message,
              error));
    }
  }

  /** Prints a ignore log entry in JSON format. */
  public static void testsIgnoredLog(String function, String args, long startTime) {
    if (TESTS_ENV) {
      System.out.println(
          new TestsLogger(
              function, args, System.currentTimeMillis() - startTime, IGNORED, null, null, null));
    }
  }

  /** Read object content of the given url. */
  public static byte[] readObject(String urlString) throws Exception {
    Request request =
        new Request.Builder().url(HttpUrl.parse(urlString)).method("GET", null).build();
    try (Response response = newHttpClient().newCall(request).execute()) {
      if (response.isSuccessful()) return response.body().bytes();
      String errorXml = response.body().string();
      throw new Exception(
          "failed to create object. Response: " + response + ", Response body: " + errorXml);
    }
  }

  /** Write data to given object url. */
  public static void writeObject(String urlString, byte[] dataBytes) throws Exception {
    // Set header 'x-amz-acl' to 'bucket-owner-full-control', so objects created
    // anonymously, can be downloaded by bucket owner in AWS S3.
    Request request =
        new Request.Builder()
            .url(HttpUrl.parse(urlString))
            .method("PUT", RequestBody.create(dataBytes, null))
            .addHeader("x-amz-acl", "bucket-owner-full-control")
            .build();
    try (Response response = newHttpClient().newCall(request).execute()) {
      if (!response.isSuccessful()) {
        String errorXml = response.body().string();
        throw new Exception(
            "failed to create object. Response: " + response + ", Response body: " + errorXml);
      }
    }
  }

  public static String getSha256Sum(InputStream stream, int len) throws Exception {
    Checksum.Hasher hasher = Checksum.Algorithm.SHA256.hasher();
    // 16KiB buffer for optimization
    byte[] buf = new byte[16384];
    int bytesToRead = buf.length;
    int bytesRead = 0;
    int totalBytesRead = 0;
    while (totalBytesRead < len) {
      if ((len - totalBytesRead) < bytesToRead) bytesToRead = len - totalBytesRead;
      bytesRead = stream.read(buf, 0, bytesToRead);
      Assertions.assertFalse(
          bytesRead < 0, "data length mismatch. expected: " + len + ", got: " + totalBytesRead);
      if (bytesRead > 0) {
        hasher.update(buf, 0, bytesRead);
        totalBytesRead += bytesRead;
      }
    }
    return Checksum.hexString(hasher.sum()).toLowerCase(Locale.US);
  }

  public static void skipStream(InputStream stream, int len) throws Exception {
    // 16KiB buffer for optimization
    byte[] buf = new byte[16384];
    int bytesToRead = buf.length;
    int bytesRead = 0;
    int totalBytesRead = 0;
    while (totalBytesRead < len) {
      if ((len - totalBytesRead) < bytesToRead) bytesToRead = len - totalBytesRead;
      bytesRead = stream.read(buf, 0, bytesToRead);
      Assertions.assertFalse(
          bytesRead < 0, "insufficient data. expected: " + len + ", got: " + totalBytesRead);
      if (bytesRead > 0) totalBytesRead += bytesRead;
    }
  }

  public static void handleException(String methodName, String args, long startTime, Exception e)
      throws Exception {
    if (e instanceof ErrorResponseException) {
      int code = ((ErrorResponseException) e).response().code();
      if (code == 405 || code == 501) {
        testsIgnoredLog(methodName, args, startTime);
        return;
      }
    }

    if (TESTS_ENV) {
      testsFailedLog(
          methodName,
          args,
          startTime,
          null,
          e.toString() + " >>> " + Arrays.toString(e.getStackTrace()));
      if (IS_RUN_ON_FAIL) return;
    } else {
      System.out.println("<FAILED> " + methodName + " " + ((args == null) ? "" : args));
    }

    throw e;
  }

  public static boolean downloadObstorServer() throws IOException {
    String url = "https://dl.pgg.net/server/obstor/release/";
    if (OS.contains("linux")) {
      url += "linux-amd64/obstor";
    } else if (OS.contains("windows")) {
      url += "windows-amd64/obstor.exe";
    } else if (OS.contains("mac")) {
      url += "darwin-amd64/obstor";
    } else {
      System.out.println("unknown operating system " + OS);
      return false;
    }

    File file = new File(OBSTOR_BINARY);
    if (file.exists()) return true;

    System.out.println("downloading " + OBSTOR_BINARY + " binary");

    Request request = new Request.Builder().url(HttpUrl.parse(url)).method("GET", null).build();
    try (Response response = newHttpClient().newCall(request).execute()) {
      if (!response.isSuccessful()) {
        System.out.println("failed to download binary " + OBSTOR_BINARY);
        return false;
      }

      BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File(OBSTOR_BINARY)));
      bufferedSink.writeAll(response.body().source());
      bufferedSink.flush();
      bufferedSink.close();
    }

    if (!OS.contains("windows")) file.setExecutable(true);

    return true;
  }

  public static Process runObstorServer(boolean tls) throws Exception {
    File binaryPath = new File(new File(System.getProperty("user.dir")), OBSTOR_BINARY);
    ProcessBuilder pb;
    if (tls) {
      pb =
          new ProcessBuilder(
              binaryPath.getPath(),
              "server",
              "--address",
              ":9001",
              "--certs-dir",
              ".cfg/certs",
              ".d{1...4}");
    } else {
      pb = new ProcessBuilder(binaryPath.getPath(), "server", ".d{1...4}");
    }

    Map<String, String> env = pb.environment();
    env.put("OBSTOR_ROOT_USER", "obstor");
    env.put("OBSTOR_ROOT_PASSWORD", "obstor123");
    env.put("OBSTOR_CI_CD", "1");
    // echo -n abcdefghijklmnopqrstuvwxyzABCDEF | base64 -
    env.put("OBSTOR_KMS_SECRET_KEY", "my-obstor-key:YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXpBQkNERUY=");
    env.put("OBSTOR_NOTIFY_WEBHOOK_ENABLE_obstorjavatest", "on");
    env.put("OBSTOR_NOTIFY_WEBHOOK_ENDPOINT_obstorjavatest", "http://example.org/");

    pb.redirectErrorStream(true);
    pb.redirectOutput(ProcessBuilder.Redirect.to(new File(OBSTOR_BINARY + ".log")));

    if (tls) {
      System.out.println("starting obstor server in TLS");
    } else {
      System.out.println("starting obstor server");
    }
    Process p = pb.start();
    Thread.sleep(10 * 1000); // wait for 10 seconds to do real start.
    return p;
  }
}
