/*
 * MinIO Java SDK for Amazon S3 Compatible Cloud Storage, (C) 2017, 2018 MinIO, Inc.
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

import net.obstor.ObstorClient;
import net.obstor.SetBucketNotificationArgs;
import net.obstor.errors.ObstorException;
import net.obstor.messages.EventType;
import net.obstor.messages.NotificationConfiguration;
import java.util.Arrays;

public class SetBucketNotification {
  /** ObstorClient.setBucketNotification() example. */
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

    NotificationConfiguration config =
        new NotificationConfiguration(
            null,
            Arrays.asList(
                new NotificationConfiguration.QueueConfiguration[] {
                  // Add a new SQS configuration.
                  new NotificationConfiguration.QueueConfiguration(
                      "arn:obstor:sqs::1:webhook",
                      null,
                      Arrays.asList(
                          new String[] {
                            EventType.OBJECT_CREATED_PUT.toString(),
                            EventType.OBJECT_CREATED_COPY.toString()
                          }),
                      new NotificationConfiguration.Filter("images", "pg"))
                }),
            null,
            null);

    // Set updated notification configuration.
    obstorClient.setBucketNotification(
        SetBucketNotificationArgs.builder().bucket("my-bucket").config(config).build());
    System.out.println("Bucket notification is set successfully");
  }
}
