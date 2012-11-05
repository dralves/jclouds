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

package org.jclouds.oauth.v2;

import org.jclouds.apis.BaseContextLiveTest;
import org.jclouds.rest.AuthorizationException;
import org.jclouds.rest.RestContext;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.oauth.v2.OAuthConstants.AUDIENCE;
import static org.jclouds.oauth.v2.OAuthConstants.SCOPES;
import static org.jclouds.oauth.v2.OAuthConstants.SIGNATURE_OR_MAC_ALGORITHM;
import static org.jclouds.oauth.v2.OAuthTestUtils.setCredentialFromPemFile;

/**
 * A base test of oauth authenticated rest providers. Providers must set the following properties:
 * <p/>
 * - oauth.endpoint
 * - oauth.audience
 * - oauth.signature-or-mac-algorithm
 * - oauth.scopes (optional, providers may choose to annotate REST methods or classes with OAuthScopes)
 *
 * @author David Alves
 */

@Test(groups = "live")
public abstract class BaseOauthAuthenticatedContextLiveTest<Api, AsyncApi> extends BaseContextLiveTest<RestContext<Api,
        AsyncApi>> {


   @BeforeGroups(groups = {"integration", "live"})
   @Override
   public void setupContext() {
      super.setupContext();
   }


   @Override
   protected Properties setupProperties() {
      Properties props = super.setupProperties();
      setCredentialFromPemFile(props, "oauth.credential");
      checkNotNull(setIfTestSystemPropertyPresent(props, "oauth.endpoint"), "test.oauth.endpoint must be set");
      checkNotNull(setIfTestSystemPropertyPresent(props, AUDIENCE), "test.oauth.audience must be set");
      setIfTestSystemPropertyPresent(props, SCOPES);
      setIfTestSystemPropertyPresent(props, SIGNATURE_OR_MAC_ALGORITHM);
      return props;
   }


   /**
    * Subclasses must override and call one of the REST api methods.
    * <p/>
    * This is just for the purpose of testing authentication so the chosen method should have no side effects (e.g.
    * listSomething()).
    * <p/>
    */
   public abstract void testCallRestApi() throws AuthorizationException;


   @AfterGroups(groups = "live")
   protected void tearDown() {
      if (context != null)
         context.close();
   }

}
