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
package org.jclouds.crypto;

import static com.google.common.base.Preconditions.checkNotNull;

import java.security.SecureRandom;
import java.util.Map;

import javax.inject.Singleton;

import com.google.inject.Inject;

@Singleton
class RsaSshKeyPairGenerator implements SshKeyPairGenerator {
   private final Crypto crypto;
   private final SecureRandom secureRandom;

   @Inject
   private RsaSshKeyPairGenerator(Crypto crypto, SecureRandom secureRandom) {
      this.crypto = checkNotNull(crypto, "crypto");
      this.secureRandom = checkNotNull(secureRandom, "secureRandom");
   }

   @Override
   public Map<String, String> get() {
      return SshKeys.generate(crypto.rsaKeyPairGenerator(), secureRandom);
   }
}
