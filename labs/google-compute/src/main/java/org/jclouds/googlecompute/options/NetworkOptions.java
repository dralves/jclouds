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

package org.jclouds.googlecompute.options;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.jclouds.googlecompute.domain.Instance;

import java.util.Set;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Options when creating a network.
 *
 * @author David Alves
 */
public class NetworkOptions {

   private final String name;
   private final String range;
   private final Optional<String> gateway;
   private final Set<Instance.NetworkInterface.AccessConfig> accessConfigs;

   public NetworkOptions(String name, String range, String gateway,
                         Set<Instance.NetworkInterface.AccessConfig> accessConfigs) {
      this.name = checkNotNull(name, "name");
      this.range = checkNotNull(range, "range");
      this.gateway = fromNullable(gateway);
      this.accessConfigs = checkNotNull(accessConfigs, "accessConfigs");
   }

   public Optional<String> getGateway() {
      return gateway;
   }

   public String getName() {
      return name;
   }

   public String getRange() {
      return range;
   }

   public Set<Instance.NetworkInterface.AccessConfig> getAccessConfigs() {
      return accessConfigs;
   }

   public static Builder builder() {
      return new Builder();
   }

   public static class Builder {
      private String name;
      private String range;
      private String gateway;
      private ImmutableSet.Builder<Instance.NetworkInterface.AccessConfig> accessConfigs = ImmutableSet.builder();

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder range(String range) {
         this.range = range;
         return this;
      }

      public Builder gateway(String gateway) {
         this.gateway = gateway;
         return this;
      }

      public Builder addAccessConfig(Instance.NetworkInterface.AccessConfig accessConfig) {
         this.accessConfigs.add(accessConfig);
         return this;
      }

      public Builder accessConfigs(Set<Instance.NetworkInterface.AccessConfig> accessConfigs) {
         this.accessConfigs = ImmutableSet.builder();
         this.accessConfigs.addAll(accessConfigs);
         return this;
      }

      public NetworkOptions build() {
         return new NetworkOptions(name, range, gateway, accessConfigs.build());
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return Objects.hashCode(name);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      NetworkOptions that = NetworkOptions.class.cast(obj);
      return equal(this.name, that.name);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return string().toString();
   }

   /**
    * {@inheritDoc}
    */
   public Objects.ToStringHelper string() {
      return toStringHelper(this)
              .omitNullValues()
              .add("name", name)
              .add("range", range)
              .add("gateway", gateway.orNull())
              .add("accessConfigs", accessConfigs);
   }
}
