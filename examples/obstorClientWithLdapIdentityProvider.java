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
import net.obstor.StatObjectArgs;
import net.obstor.StatObjectResponse;
import net.obstor.credentials.LdapIdentityProvider;
import net.obstor.credentials.Provider;
import net.obstor.errors.ObstorException;

public class ObstorClientWithLdapIdentityProvider {
  public static void main(String[] args) throws ObstorException {
    // STS endpoint usually point to Obstor server.
    String stsEndpoint = "http://STS-HOST:STS-PORT/";

    // LDAP username.
    String ldapUsername = "USERNAME";

    // LDAP password.
    String ldapPassword = "PASSWORD";

    Provider provider = new LdapIdentityProvider(stsEndpoint, ldapUsername, ldapPassword, null);

    ObstorClient obstorClient =
        ObstorClient.builder()
            .endpoint("https://OBSTOR-HOST:OBSTOR-PORT")
            .credentialsProvider(provider)
            .build();

    // Get information of an object.
    StatObjectResponse stat =
        obstorClient.statObject(
            StatObjectArgs.builder().bucket("my-bucket").object("my-object").build());
    System.out.println(stat);
  }
}
