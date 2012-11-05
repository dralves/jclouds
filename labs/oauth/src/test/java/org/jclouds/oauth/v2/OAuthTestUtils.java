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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.jclouds.oauth.v2.OAuthConstants.TOKEN_AUDIENCE;

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
         properties.put(TOKEN_AUDIENCE, "https://accounts.google.com/o/oauth2/token");
         return properties;
      } catch (IOException e) {
         throw Throwables.propagate(e);
      }
   }

   /**
    * Loads the set of properties inside a properties file given an file location. Transforms the namespace of the
    * properties inside the file from oauth to whatever provider is passed as argument. Allows to have a single
    * properties file for multiple providers that use the same oauth identity and pk.
    * <p/>
    * Usually the properties that can be found inside the file are:
    * - oauth.identity (mandatory) - the oauth account id
    * - oauth.credential (mandatory) - the oauth private key
    * - oauth.endpoint (optional) - the oauth endpoint to use for authentication and authorization
    */
   public static Properties loadPropertiesFile(Properties properties, String callerProvider) throws IOException {
      checkNotNull(callerProvider);
      if (properties == null) {
         properties = new Properties(System.getProperties());
      }
      String propertiesFilePath = (String) properties.get("test.oauth.properties");
      if (propertiesFilePath != null && new File(propertiesFilePath).exists()) {
         properties.load(new FileReader(propertiesFilePath));
      }
      checkState(properties.contains("oauth.identity"));
      checkState(properties.contains("oauth.credential"));
      properties.put(callerProvider + ".identity", properties.get("oauth.identity"));
      properties.put(callerProvider + ".credential", properties.get("oauth.credential"));
      return properties;
   }
}
