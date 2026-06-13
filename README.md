# Obstor Java SDK for Amazon S3 Compatible Cloud Storage

Obstor Java SDK is Simple Storage Service (aka S3) client to perform bucket and object operations to any Amazon S3 compatible object storage service.

For a complete list of APIs and examples, please take a look at the [Java Client API Reference](https://obstor.net/docs/enterprise/obstor-object-store/developers/sdk/java/api/) documentation.

## Minimum Requirements
Java 1.8 or above.

## Maven usage
```xml
<dependency>
    <groupId>net.obstor</groupId>
    <artifactId>obstor</artifactId>
    <version>9.0.2</version>
</dependency>
```

## Gradle usage
```
dependencies {
    implementation("net.obstor:obstor:9.0.2")
}
```

## JAR download
The latest JAR can be downloaded from [here](https://repo1.maven.org/maven2/io/obstor/obstor/9.0.2/)

## Quick Start Example - File Uploader
This example program connects to an object storage server, makes a bucket on the server and then uploads a file to the bucket.

You need three items in order to connect to an object storage server.

| Parameters | Description                                                |
|------------|------------------------------------------------------------|
| Endpoint   | URL to S3 service.                                         |
| Access Key | Access key (aka user ID) of an account in the S3 service.  |
| Secret Key | Secret key (aka password) of an account in the S3 service. |

This example uses Obstor server playground [https://demo.obstor.net](https://demo.obstor.net). Feel free to use this service for test and development.

### FileUploader.java
```java
import net.obstor.BucketExistsArgs;
import net.obstor.MakeBucketArgs;
import net.obstor.ObstorClient;
import net.obstor.UploadObjectArgs;
import net.obstor.errors.ObstorException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
  public static void main(String[] args)
      throws IOException, NoSuchAlgorithmException, InvalidKeyException {
    try {
      // Create a obstorClient with the Obstor server playground, its access key and secret key.
      ObstorClient obstorClient =
          ObstorClient.builder()
              .endpoint("https://demo.obstor.net")
              .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
              .build();

      // Make 'asiatrip' bucket if not exist.
      boolean found =
          obstorClient.bucketExists(BucketExistsArgs.builder().bucket("asiatrip").build());
      if (!found) {
        // Make a new bucket called 'asiatrip'.
        obstorClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
      } else {
        System.out.println("Bucket 'asiatrip' already exists.");
      }

      // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
      // 'asiatrip'.
      obstorClient.uploadObject(
          UploadObjectArgs.builder()
              .bucket("asiatrip")
              .object("asiaphotos-2015.zip")
              .filename("/home/user/Photos/asiaphotos.zip")
              .build());
      System.out.println(
          "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
              + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
    } catch (ObstorException e) {
      System.out.println("Error occurred: " + e);
      System.out.println("HTTP trace: " + e.httpTrace());
    }
  }
}
```

#### Compile FileUploader
```sh
$ javac -cp obstor-9.0.2-all.jar FileUploader.java
```

#### Run FileUploader
```sh
$ java -cp obstor-9.0.2-all.jar:. FileUploader
'/home/user/Photos/asiaphotos.zip' is successfully uploaded as object 'asiaphotos-2015.zip' to bucket 'asiatrip'.

$ mc ls demo/asiatrip/
[2016-06-02 18:10:29 PDT]  82KiB asiaphotos-2015.zip
```

## More References
* [Java Client API Reference](https://obstor.net/docs/enterprise/obstor-object-store/developers/sdk/java/api/)
* [Javadoc](https://obstor.net/docs/java)
* [Examples](https://github.com/obstor/obstor-java/tree/release/examples)

## Explore Further
* [Complete Documentation](https://obstor.net/docs/enterprise/obstor-object-store/)
* [Build your own Photo API Service - Full Application Example ](https://github.com/obstor/obstor-java-rest-example)

## Contribute
Please refer [Contributors Guide](https://github.com/obstor/obstor-java/blob/release/CONTRIBUTING.md)
