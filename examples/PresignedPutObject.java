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

import net.obstor.GetPresignedObjectUrlArgs;
import net.obstor.Http;
import net.obstor.ObstorClient;
import net.obstor.errors.ObstorException;
import java.util.HashMap;
import java.util.Map;

public class PresignedPutObject {
  /** ObstorClient.presignedPutObject() example. */
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

    // Get presigned URL string to upload 'my-object' in 'my-bucket'
    // with response-content-type as application/json and its life time is
    // one day.
    Map<String, String> reqParams = new HashMap<String, String>();
    reqParams.put("response-content-type", "application/json");

    String url =
        obstorClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Http.Method.PUT)
                .bucket("my-bucket")
                .object("my-object")
                .expiry(60 * 60 * 24)
                .extraQueryParams(reqParams)
                .build());
    System.out.println(url);
  }
}
