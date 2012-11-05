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
package org.jclouds.oauth.v2.functions;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jclouds.oauth.v2.config.OAuthScopes;
import org.jclouds.oauth.v2.domain.ClaimSet;
import org.jclouds.oauth.v2.domain.Header;
import org.jclouds.oauth.v2.domain.OAuthCredentials;
import org.jclouds.oauth.v2.domain.TokenRequest;
import org.jclouds.oauth.v2.domain.TokenRequestFormat;
import org.jclouds.rest.internal.GeneratedHttpRequest;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;
import static org.jclouds.oauth.v2.OAuthConstants.ADDITIONAL_CLAIMS;
import static org.jclouds.oauth.v2.OAuthConstants.SCOPES;
import static org.jclouds.oauth.v2.OAuthConstants.SIGNATURE_OR_MAC_ALGORITHM;
import static org.jclouds.oauth.v2.OAuthConstants.AUDIENCE;

/**
 * The default authenticator.
 * <p/>
 * Builds the default token request with the following claims: iss,scope,aud,iat,exp.
 * <p/>
 * TODO scopes etc should come from the REST method and not from a global property
 *
 * @author David Alves
 */
@Singleton
public class BuildTokenRequest implements Function<GeneratedHttpRequest, TokenRequest> {

   private final String assertionTargetDescription;
   private final String signatureAlgorithm;
   private final TokenRequestFormat tokenRequestFormat;
   private Supplier<OAuthCredentials> credentialsSupplier;

   @Inject(optional = true)
   @Named(ADDITIONAL_CLAIMS)
   protected Map<String, String> additionalClaims = ImmutableMap.of();

   @Inject(optional = true)
   @Named(SCOPES)
   protected String globalScopes = null;

   @VisibleForTesting
   public Ticker ticker = Ticker.systemTicker();

   @Inject
   public BuildTokenRequest(@Named(AUDIENCE) String assertionTargetDescription,
                            @Named(SIGNATURE_OR_MAC_ALGORITHM) String signatureAlgorithm,
                            TokenRequestFormat tokenRequestFormat, Supplier<OAuthCredentials> credentialsSupplier) {
      this.assertionTargetDescription = assertionTargetDescription;
      this.signatureAlgorithm = signatureAlgorithm;
      this.tokenRequestFormat = tokenRequestFormat;
      this.credentialsSupplier = credentialsSupplier;
   }

   @Override
   public TokenRequest apply(GeneratedHttpRequest request) {
      long now = TimeUnit.SECONDS.convert(ticker.read(), TimeUnit.NANOSECONDS);

      // fetch the token
      Header header = new Header.Builder()
              .signerAlgorithm(signatureAlgorithm)
              .type(tokenRequestFormat.getTypeName())
              .build();

      ClaimSet claimSet = new ClaimSet.Builder(this.tokenRequestFormat.requiredClaims())
              .addClaim("iss", credentialsSupplier.get().identity)
              .addClaim("scope", getOAuthScopes(request))
              .addClaim("aud", assertionTargetDescription)
              .emissionTime(now)
              .expirationTime(now + 3600)
              .addAllClaims(additionalClaims)
              .build();

      return new TokenRequest.Builder()
              .header(header)
              .claimSet(claimSet)
              .build();
   }

   protected String getOAuthScopes(GeneratedHttpRequest request) {
      OAuthScopes classScopes = request.getDeclaring().getAnnotation(OAuthScopes.class);
      OAuthScopes methodScopes = request.getJavaMethod().getAnnotation(OAuthScopes.class);

      // if no annotations are present the rely on globally set scopes
      if (classScopes == null && methodScopes == null) {
         checkState(globalScopes != null, String.format("REST class or method should be annotated " +
                 "with OAuthScopes specifying required permissions. Alternatively a global property " +
                 "\"oauth.scopes\" may be set to define scopes globally. REST Class: %s, Method: %s",
                 request.getDeclaring().getName(),
                 request.getJavaMethod().getName()));
         return globalScopes;
      }

      OAuthScopes scopes = methodScopes != null ? methodScopes : classScopes;
      return Joiner.on(",").join(scopes.value());
   }
}
