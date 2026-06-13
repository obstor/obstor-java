import com.google.common.io.ByteStreams;
import net.obstor.GetObjectArgs;
import net.obstor.ObstorClient;
import net.obstor.StatObjectArgs;
import net.obstor.StatObjectResponse;
import net.obstor.errors.ObstorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GetObjectResume {
  public static void main(String[] args) throws IOException, ObstorException {
    ObstorClient obstorClient =
        ObstorClient.builder()
            .endpoint("https://demo.obstor.net")
            .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
            .build();

    String filename = "my-object";
    Path path = Paths.get(filename);
    long fileSize = 0;
    if (Files.exists(path)) fileSize = Files.size(path);

    StatObjectResponse stat =
        obstorClient.statObject(
            StatObjectArgs.builder().bucket("my-bucket").object("my-object").build());

    if (fileSize == stat.size()) {
      // Already fully downloaded.
      return;
    }

    if (fileSize > stat.size()) {
      throw new IOException("stored file size is greater than object size");
    }

    InputStream stream =
        obstorClient.getObject(
            GetObjectArgs.builder()
                .bucket("my-bucket")
                .object("my-object")
                .offset(fileSize)
                .build());

    try (OutputStream os =
        Files.newOutputStream(
            path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
      ByteStreams.copy(stream, os);
    } finally {
      stream.close();
    }
  }
}
