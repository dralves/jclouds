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
package org.jclouds.oauth.v2;

import com.google.common.base.Throwables;
import org.jclouds.util.Strings2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.jclouds.oauth.v2.OAuthConstants.TOKEN_ASSERTION_DESCRIPTION;

/**
 * Utils for OAuth tests.
 *
 * @author David Alves
 */
public class OAuthTestUtils {

   public static Properties defaultProperties(Properties properties) {
      try {
         properties = properties == null ? new Properties() : properties;
         properties.put("oauth.identity", "foo");
         properties.put("oauth.credential", Strings2.toStringAndClose(new FileInputStream("src/test/resources/testpk" +
                 ".pem")));
         properties.put("oauth.endpoint", "http://localhost:5000/o/oauth2/token");
         properties.put(TOKEN_ASSERTION_DESCRIPTION, "https://accounts.google.com/o/oauth2/token");
         return properties;
      } catch (IOException e) {
         throw Throwables.propagate(e);
      }
   }
}
