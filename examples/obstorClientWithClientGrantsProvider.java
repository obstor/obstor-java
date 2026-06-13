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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import net.obstor.ObstorClient;
import net.obstor.StatObjectArgs;
import net.obstor.StatObjectResponse;
import net.obstor.credentials.ClientGrantsProvider;
import net.obstor.credentials.Jwt;
import net.obstor.credentials.Provider;
import net.obstor.errors.ObstorException;
import java.io.IOException;
import java.security.ProviderException;
import java.util.Objects;
import javax.annotation.Nonnull;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ObstorClientWithClientGrantsProvider {
  static Jwt getJwt(
      @Nonnull String clientId, @Nonnull String clientSecret, @Nonnull String idpEndpoint) {
    Objects.requireNonNull(clientId, "Client id must not be null");
    Objects.requireNonNull(clientSecret, "ClientSecret must not be null");

    RequestBody requestBody =
        new FormBody.Builder()
            .add("username", clientId)
            .add("password", clientSecret)
            .add("grant_type", "client_credentials")
            .build();

    Request request = new Request.Builder().url(idpEndpoint).post(requestBody).build();

    OkHttpClient client = new OkHttpClient();
    try (Response response = client.newCall(request).execute()) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      mapper.setVisibility(
          VisibilityChecker.Std.defaultInstance()
              .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
      return mapper.readValue(response.body().charStream(), Jwt.class);
    } catch (IOException e) {
      throw new ProviderException(e);
    }
  }

  public static void main(String[] args) throws ObstorException {
    // IDP endpoint.
    String idpEndpoint =
        "https://IDP-HOST:IDP-PORT/auth/realms/master/protocol/openid-connect/token";

    // Client-ID to fetch JWT.
    String clientId = "IDP-CLIENT-ID";

    // Client secret to fetch JWT.
    String clientSecret = "IDP-CLIENT-SECRET";

    // STS endpoint usually point to Obstor server.
    String stsEndpoint = "http://STS-HOST:STS-PORT/";

    // Policy to Credentials. - optional, primary policy
    // is inherited from JWT claims mapping.
    String policy = null;

    //
    // String policy =
    //     new StringBuilder()
    //         .append("{\n")
    //         .append("    \"Statement\": [\n")
    //         .append("       " + " {\n")
    //         .append("            \"Action\": [\n")
    //         .append("                \"s3:GetBucketLocation\",\n")
    //         .append("                \"s3:ListBucket\"\n")
    //         .append("            ],\n")
    //         .append("            \"Effect\": \"Allow\",\n")
    //         .append("            \"Principal\": \"*\",\n")
    //         .append("            \"Resource\": \"arn:aws:s3:::test\"\n")
    //         .append("        }\n")
    //         .append("    ],\n")
    //         .append("    \"Version\": \"2012-10-17\"\n")
    //         .append("}\n")
    //         .toString();

    Provider provider =
        new ClientGrantsProvider(
            () -> getJwt(clientId, clientSecret, idpEndpoint), stsEndpoint, null, policy, null);

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
