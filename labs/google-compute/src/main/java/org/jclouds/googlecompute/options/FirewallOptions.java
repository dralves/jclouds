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

import com.google.common.collect.ImmutableSet;
import org.jclouds.googlecompute.domain.Firewall;

import java.net.URI;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author David Alves
 */
public class FirewallOptions {

   private final String name;
   private final URI network;
   private final Set<String> sourceRanges;
   private final Set<String> sourceTags;
   private final Set<String> targetTags;
   private final Set<Firewall.Rule> allowed;

   public FirewallOptions(String name, URI network, Set<String> sourceRanges, Set<String> sourceTags,
                          Set<String> targetTags, Set<Firewall.Rule> allowed) {
      this.allowed = checkNotNull(allowed, "allowed");
      this.name = checkNotNull(name, "name");
      this.network = checkNotNull(network, "network");
      this.sourceRanges = checkNotNull(sourceRanges, "sourceRanges");
      this.sourceTags = checkNotNull(sourceTags, "sourceTags");
      this.targetTags = checkNotNull(targetTags, "targetTags");
   }

   public Set<Firewall.Rule> getAllowed() {
      return allowed;
   }

   public String getName() {
      return name;
   }

   public URI getNetwork() {
      return network;
   }

   public Set<String> getSourceRanges() {
      return sourceRanges;
   }

   public Set<String> getSourceTags() {
      return sourceTags;
   }

   public Set<String> getTargetTags() {
      return targetTags;
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return new Builder()
              .name(this.name)
              .sourceRanges(this.sourceRanges)
              .sourceTags(this.sourceTags)
              .targetTags(this.targetTags)
              .allowedRules(this.allowed);
   }

   public static final class Builder {

      private String name;
      private URI network;
      private ImmutableSet.Builder<String> sourceRanges = ImmutableSet.builder();
      private ImmutableSet.Builder<String> sourceTags = ImmutableSet.builder();
      private ImmutableSet.Builder<String> targetTags = ImmutableSet.builder();
      private ImmutableSet.Builder<Firewall.Rule> allowed = ImmutableSet.builder();

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder network(URI network) {
         this.network = network;
         return this;
      }

      public Builder addSourceRange(String sourceRange) {
         this.sourceRanges.add(sourceRange);
         return this;
      }

      public Builder sourceRanges(Set<String> sourceRanges) {
         this.sourceRanges = ImmutableSet.builder();
         this.sourceRanges.addAll(sourceRanges);
         return this;
      }

      public Builder addTargetTag(String targetTag) {
         this.targetTags.add(targetTag);
         return this;
      }

      public Builder targetTags(Set<String> targetTags) {
         this.targetTags = ImmutableSet.builder();
         this.targetTags.addAll(targetTags);
         return this;
      }

      public Builder addSourceTag(String sourceTag) {
         this.sourceTags.add(sourceTag);
         return this;
      }

      public Builder sourceTags(Set<String> sourceTags) {
         this.sourceTags = ImmutableSet.builder();
         return this;
      }

      public Builder addAllowedRule(Firewall.Rule allowedRule) {
         this.allowed.add(allowedRule);
         return this;
      }

      public Builder allowedRules(Set<Firewall.Rule> allowedRules) {
         this.allowed = ImmutableSet.builder();
         this.allowed.addAll(allowedRules);
         return this;
      }

      public FirewallOptions build() {
         return new FirewallOptions(name, network, sourceRanges.build(), sourceTags.build(), targetTags.build(),
                 allowed.build());
      }

   }
}
