/*
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jclouds.googlestorage;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.inject.Module;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.googlestorage.config.GoogleStorageRestClientModule;
import org.jclouds.oauth.v2.config.OAuthAuthenticationModule;
import org.jclouds.rest.RestContext;
import org.jclouds.rest.internal.BaseRestApiMetadata;
import org.jclouds.s3.S3ApiMetadata;
import org.jclouds.s3.S3AsyncClient;
import org.jclouds.s3.S3Client;
import org.jclouds.s3.blobstore.S3BlobStoreContext;
import org.jclouds.s3.blobstore.config.S3BlobStoreContextModule;

import java.net.URI;
import java.util.Properties;

import static org.jclouds.Constants.PROPERTY_ISO3166_CODES;
import static org.jclouds.oauth.v2.OAuthConstants.AUDIENCE;
import static org.jclouds.oauth.v2.OAuthConstants.SCOPES;
import static org.jclouds.oauth.v2.OAuthConstants.SIGNATURE_OR_MAC_ALGORITHM;

public class GoogleStorageApiMetadata extends BaseRestApiMetadata {


   public static final TypeToken<RestContext<S3Client, GoogleStorageAsyncClient>> CONTEXT_TOKEN =
           new TypeToken<RestContext<S3Client, GoogleStorageAsyncClient>>() {};

   @Override
   public Builder toBuilder() {
      return new Builder(getApi(), getAsyncApi()).fromApiMetadata(this);
   }

   public GoogleStorageApiMetadata() {
      this(new Builder(S3Client.class, S3AsyncClient.class));
   }

   protected GoogleStorageApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = S3ApiMetadata.defaultProperties();
      properties.setProperty(PROPERTY_ISO3166_CODES, "US");
      properties.put("oauth.endpoint", "https://accounts.google.com/o/oauth2/token");
      properties.put(AUDIENCE, "https://accounts.google.com/o/oauth2/token");
      properties.put(SIGNATURE_OR_MAC_ALGORITHM, "RS256");
      properties.put(SCOPES, "https://www.googleapis.com/auth/devstorage.full_control");
      return properties;
   }

   public static class Builder extends BaseRestApiMetadata.Builder {

      protected Builder(Class<?> syncClient, Class<?> asyncClient) {
         super(syncClient, asyncClient);
         id("googlestorage")
                 .name("Google Cloud Storage (v1) API")
                 .identityName("client_id")
                 .credentialName("client_secret")
                 .defaultEndpoint("http://storage.googleapis.com")
                 .version("v1")
                 .documentation(URI.create("https://developers.google.com/storage/docs/json_api/"))
                 .defaultProperties(GoogleStorageApiMetadata.defaultProperties())
                 .context(CONTEXT_TOKEN)
                 .view(TypeToken.of(S3BlobStoreContext.class))
                 .defaultModules(ImmutableSet.<Class<? extends Module>>of(GoogleStorageRestClientModule.class,
                         S3BlobStoreContextModule.class, OAuthAuthenticationModule.class));
      }

      @Override
      public ApiMetadata build() {
         return new GoogleStorageApiMetadata(this);
      }

      @Override
      public Builder fromApiMetadata(ApiMetadata in) {
         super.fromApiMetadata(in);
         return this;
      }
   }

}