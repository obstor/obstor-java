/*
 * MinIO Java SDK for Amazon S3 Compatible Cloud Storage, (C) 2015 MinIO, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.obstor.CloseableIterator;
import net.obstor.ListenBucketNotificationArgs;
import net.obstor.ObstorClient;
import net.obstor.Result;
import net.obstor.errors.ObstorException;
import net.obstor.messages.NotificationRecords;
import java.io.IOException;

public class ListenBucketNotification {
  /** ObstorClient.listenBucketNotification() example. */
  public static void main(String[] args) throws ObstorException {
    /* demo.obstor.net for test and development. */
    ObstorClient obstorClient =
        ObstorClient.builder()
            .endpoint("https://demo.obstor.net")
            .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
            .build();

    /* Amazon S3: */
    // ObstorClient obstorClient =
    //     ObstorClient.builder()
    //         .endpoint("https://s3.amazonaws.com")
    //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
    //         .build();

    String[] events = {"s3:ObjectCreated:*", "s3:ObjectAccessed:*"};
    try (CloseableIterator<Result<NotificationRecords>> ci =
        obstorClient.listenBucketNotification(
            ListenBucketNotificationArgs.builder()
                .bucket("bucketName")
                .prefix("")
                .suffix("")
                .events(events)
                .build())) {
      while (ci.hasNext()) {
        NotificationRecords records = ci.next().get();
        NotificationRecords.Event event = records.events().get(0);
        System.out.println(
            event.bucket().name() + "/" + event.object().key() + " has been created");
      }
    } catch (IOException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}
