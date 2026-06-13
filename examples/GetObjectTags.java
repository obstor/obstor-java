/*
 * MinIO Java SDK for Amazon S3 Compatible Cloud Storage, (C) 2020 MinIO, Inc.
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

import net.obstor.GetObjectTagsArgs;
import net.obstor.ObstorClient;
import net.obstor.errors.ObstorException;
import net.obstor.messages.Tags;

public class GetObjectTags {
  /** ObstorClient.getObjectTags() example. */
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

    Tags tags =
        obstorClient.getObjectTags(
            GetObjectTagsArgs.builder().bucket("my-bucket").object("my-object").build());
    System.out.println("Object tags: " + tags.get());
  }
}
