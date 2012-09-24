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
package org.jclouds.nimbula.config;

import com.google.common.collect.ImmutableMap;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.json.config.GsonModule.DateAdapter;
import org.jclouds.json.config.GsonModule.Iso8601DateAdapter;
import org.jclouds.nimbula.NimbulaAsyncClient;
import org.jclouds.nimbula.NimbulaClient;
import org.jclouds.nimbula.features.*;
import org.jclouds.nimbula.handlers.NimbulaErrorHandler;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.config.RestClientModule;

import java.util.Map;

/**
 * Configures the Nimbula connection.
 *
 * @author Adrian Cole
 */
@ConfiguresRestClient
public class NimbulaRestClientModule extends
        RestClientModule<NimbulaClient, NimbulaAsyncClient> {

    public static final String NIMBULA_MEDIA_TYPE = "application/nimbula-v2+json";
    public static final String NIMBULA_DIRECTORY_MEDIA_TYPE = "application/nimbula-v2+directory+json";

    public static final Map<Class<?>, Class<?>> DELEGATE_MAP = ImmutableMap
            .<Class<?>, Class<?>>builder()
            .put(ShapeApi.class, ShapeAsyncApi.class)
            .put(ImageListApi.class, ImageListAsyncApi.class)
            .put(InstanceApi.class, InstanceAsyncApi.class)
            .put(MachineImageApi.class, MachineImageAsyncApi.class)
            .put(UserApi.class, UserAsyncApi.class).build();

    public NimbulaRestClientModule() {
        super(DELEGATE_MAP);
    }

    @Override
    protected void configure() {
        bind(DateAdapter.class).to(Iso8601DateAdapter.class);
        super.configure();
    }

    @Override
    protected void bindErrorHandlers() {
        bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(
                NimbulaErrorHandler.class);
        bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(
                NimbulaErrorHandler.class);
        bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(
                NimbulaErrorHandler.class);
    }

}
