import net.obstor.ObstorClient;
import net.obstor.admin.ObstorAdminClient;

public class FunctionalTest {
  public static void runS3Tests(TestArgs args) throws Exception {
    if (!args.MINT_ENV) System.out.println(">>> Running S3 tests:");
    new TestObstorClient(
            args,
            args.IS_QUICK_TEST,
            ObstorClient.builder()
                .endpoint(args.endpoint)
                .credentials(args.accessKey, args.secretKey)
                .build())
        .runTests();

    if (args.automated) {
      if (!args.MINT_ENV) {
        System.out.println();
        System.out.println(">>> Running S3 tests on TLS endpoint:");
      }
      ObstorClient client =
          ObstorClient.builder()
              .endpoint(args.endpointTLS)
              .credentials(args.accessKey, args.secretKey)
              .build();
      client.ignoreCertCheck();
      new TestObstorClient(args, args.IS_QUICK_TEST, client).runTests();
    }

    if (!args.MINT_ENV) {
      System.out.println();
      System.out.println(">>> Running quick tests specific region:");
      new TestObstorClient(
              args,
              true,
              ObstorClient.builder()
                  .endpoint(args.endpoint)
                  .credentials(args.accessKey, args.secretKey)
                  .region(args.region)
                  .build())
          .runTests();
    }
  }

  public static void runObstorAdminTests(TestArgs args) throws Exception {
    if (!args.MINT_ENV) {
      System.out.println();
      System.out.println(">>> Running Obstor admin API tests:");
      new TestObstorAdminClient(
              args,
              ObstorAdminClient.builder()
                  .endpoint(args.endpoint)
                  .credentials(args.accessKey, args.secretKey)
                  .build())
          .runAdminTests();
    }
  }

  public static void runTests(TestArgs args) throws Exception {
    runS3Tests(args);
    runObstorAdminTests(args);
  }

  public static void main(String[] args) throws Exception {
    String endpoint = null;
    String accessKey = null;
    String secretKey = null;
    String region = null;
    if (args.length == 4) {
      endpoint = args[0];
      accessKey = args[1];
      secretKey = args[2];
      region = args[3];
    }
    TestArgs testArgs = new TestArgs(endpoint, accessKey, secretKey, region);

    Process obstorProcess = null;
    Process obstorProcessTLS = null;
    if (args.length != 4) {
      if (!TestArgs.downloadObstorServer()) {
        System.out.println("usage: FunctionalTest <ENDPOINT> <ACCESSKEY> <SECRETKEY> <REGION>");
        System.exit(-1);
      }

      obstorProcess = TestArgs.runObstorServer(false);
      try {
        int exitValue = obstorProcess.exitValue();
        System.out.println("obstor server process exited with " + exitValue);
        System.out.println("usage: FunctionalTest <ENDPOINT> <ACCESSKEY> <SECRETKEY> <REGION>");
        System.exit(-1);
      } catch (IllegalThreadStateException e) {
        TestArgs.ignore();
      }

      obstorProcessTLS = TestArgs.runObstorServer(true);
      try {
        int exitValue = obstorProcessTLS.exitValue();
        System.out.println("obstor server process exited with " + exitValue);
        System.out.println("usage: FunctionalTest <ENDPOINT> <ACCESSKEY> <SECRETKEY> <REGION>");
        System.exit(-1);
      } catch (IllegalThreadStateException e) {
        TestArgs.ignore();
      }
    }

    int exitValue = 0;
    try {
      runTests(testArgs);
    } catch (Exception e) {
      if (!testArgs.MINT_ENV) e.printStackTrace();
      exitValue = -1;
    } finally {
      if (obstorProcess != null) obstorProcess.destroy();
      if (obstorProcessTLS != null) obstorProcessTLS.destroy();
    }

    System.exit(exitValue);
  }
}
