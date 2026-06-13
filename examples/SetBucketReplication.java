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

import net.obstor.ObstorClient;
import net.obstor.SetBucketReplicationArgs;
import net.obstor.errors.ObstorException;
import net.obstor.messages.Filter;
import net.obstor.messages.ReplicationConfiguration;
import net.obstor.messages.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetBucketReplication {
  /** ObstorClient.setBucketReplication() example. */
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

    Map<String, String> tags = new HashMap<>();
    tags.put("key1", "value1");
    tags.put("key2", "value2");

    ReplicationConfiguration.Rule rule =
        new ReplicationConfiguration.Rule(
            Status.ENABLED,
            new ReplicationConfiguration.Destination(
                null, null, "REPLACE-WITH-ACTUAL-DESTINATION-BUCKET-ARN", null, null, null, null),
            new ReplicationConfiguration.DeleteMarkerReplication(Status.DISABLED),
            null,
            new Filter(new Filter.And("TaxDocs", tags, null, null)),
            "rule1",
            null,
            1,
            null,
            null);

    List<ReplicationConfiguration.Rule> rules = new ArrayList<>();
    rules.add(rule);

    ReplicationConfiguration config =
        new ReplicationConfiguration("REPLACE-WITH-ACTUAL-ROLE", rules);

    obstorClient.setBucketReplication(
        SetBucketReplicationArgs.builder().bucket("my-bucket").config(config).build());
  }
}
