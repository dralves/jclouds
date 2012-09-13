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
package org.jclouds.nimbula;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.inject.Module;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.nimbula.compute.config.NimbulaComputeServiceContextModule;
import org.jclouds.nimbula.config.NimbulaRestClientModule;
import org.jclouds.rest.RestContext;
import org.jclouds.rest.internal.BaseRestApiMetadata;

import java.net.URI;
import java.util.Properties;

/**
 * Implementation of {@link ApiMetadata} for Nimbula 2.0.3 API
 *
 * @author Adrian Cole
 */
public class NimbulaApiMetadata extends BaseRestApiMetadata {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6725672099385580694L;

    public static final TypeToken<RestContext<NimbulaClient, NimbulaAsyncClient>> CONTEXT_TOKEN = new TypeToken<RestContext<NimbulaClient, NimbulaAsyncClient>>() {
        private static final long serialVersionUID = -5070937833892503232L;
    };

    @Override
    public Builder toBuilder() {
        return new Builder().fromApiMetadata(this);
    }

    public NimbulaApiMetadata() {
        this(new Builder());
    }

    protected NimbulaApiMetadata(Builder builder) {
        super(builder);
    }

    public static Properties defaultProperties() {
        Properties properties = BaseRestApiMetadata.defaultProperties();
        // TODO: add any custom properties here
        return properties;
    }

    public static class Builder extends BaseRestApiMetadata.Builder {

        protected Builder() {
            super(NimbulaClient.class, NimbulaAsyncClient.class);
            id("nimbula")
                    .name("Nimbula API")
                    .identityName("user")
                    .credentialName("password")
                    .documentation(URI.create("TODO"))
                    .version("2.0.3")
                    .view(TypeToken.of(ComputeServiceContext.class))
                    .defaultEndpoint("http://api.eval.nimbula.com/")
                    .defaultProperties(NimbulaApiMetadata.defaultProperties())
                    .defaultModules(ImmutableSet.<Class<? extends Module>>of(NimbulaRestClientModule.class, NimbulaComputeServiceContextModule.class));
        }

        @Override
        public NimbulaApiMetadata build() {
            return new NimbulaApiMetadata(this);
        }

        @Override
        public Builder fromApiMetadata(ApiMetadata in) {
            super.fromApiMetadata(in);
            return this;
        }

    }

}
