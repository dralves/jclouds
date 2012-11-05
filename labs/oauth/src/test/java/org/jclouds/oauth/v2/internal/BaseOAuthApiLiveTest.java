/**
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
package org.jclouds.oauth.v2.internal;

import com.google.common.base.Ticker;
import com.google.common.reflect.TypeToken;
import org.jclouds.apis.BaseContextLiveTest;
import org.jclouds.oauth.v2.OAuthApi;
import org.jclouds.oauth.v2.OAuthApiMetadata;
import org.jclouds.oauth.v2.OAuthAsyncApi;
import org.jclouds.rest.RestContext;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.oauth.v2.OAuthConstants.AUDIENCE;
import static org.jclouds.oauth.v2.OAuthConstants.SCOPES;
import static org.jclouds.oauth.v2.OAuthConstants.SIGNATURE_OR_MAC_ALGORITHM;
import static org.jclouds.oauth.v2.OAuthTestUtils.setCredentialFromPemFile;


/**
 * @author David Alves
 */
@Test(groups = "live")
public class BaseOAuthApiLiveTest extends BaseContextLiveTest<RestContext<OAuthApi, OAuthAsyncApi>> {

   public BaseOAuthApiLiveTest() {
      provider = "oauth";
   }

   @Override
   protected TypeToken<RestContext<OAuthApi, OAuthAsyncApi>> contextType() {
      return OAuthApiMetadata.CONTEXT_TOKEN;
   }

   protected RestContext<OAuthApi, OAuthAsyncApi> oauthContext;
   protected Properties properties;

   @BeforeGroups(groups = {"integration", "live"})
   @Override
   public void setupContext() {
      super.setupContext();
      oauthContext = context;
   }

   @Override
   protected Properties setupProperties() {
      Properties props = super.setupProperties();
      setCredentialFromPemFile(props, "oauth.credential");
      checkNotNull(setIfTestSystemPropertyPresent(props, "oauth.endpoint"), "test.oauth.endpoint must be set");
      checkNotNull(setIfTestSystemPropertyPresent(props, AUDIENCE), "test.oauth.audience must be set");
      setIfTestSystemPropertyPresent(props, SCOPES);
      setIfTestSystemPropertyPresent(props, SIGNATURE_OR_MAC_ALGORITHM);
      this.properties = props;
      return props;
   }

   @AfterGroups(groups = "live")
   protected void tearDown() {
      if (oauthContext != null)
         oauthContext.close();
   }


   protected long nowInSeconds() {
      return TimeUnit.SECONDS.convert(Ticker.systemTicker().read(), TimeUnit.NANOSECONDS);
   }


}

