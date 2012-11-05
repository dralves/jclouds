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

import com.google.common.reflect.TypeToken;
import org.jclouds.oauth.v2.BaseOauthAuthenticatedContextLiveTest;
import org.jclouds.rest.AuthorizationException;
import org.jclouds.rest.RestContext;
import org.jclouds.s3.S3Client;

/**
 * @author David Alves
 */
public class GoogleStorageOauthAuthenticatedLiveTest
        extends BaseOauthAuthenticatedContextLiveTest<S3Client, GoogleStorageAsyncClient> {

   GoogleStorageOauthAuthenticatedLiveTest() {
      provider = "googlestorage";
   }


   @Override
   public void testCallRestApi() throws AuthorizationException {
      context.getApi().listOwnedBuckets();
   }


   @Override
   protected TypeToken<RestContext<S3Client, GoogleStorageAsyncClient>> contextType() {
      return GoogleStorageApiMetadata.CONTEXT_TOKEN;
   }
}
