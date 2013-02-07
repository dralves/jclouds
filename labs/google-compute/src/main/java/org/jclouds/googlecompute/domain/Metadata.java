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

package org.jclouds.googlecompute.domain;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A metadata container for multiple Resource types.
 * <p/>
 * The total size of all keys and values must be less than 512 KB.
 * <p/>
 * Key for the metadata entry: Keys must conform to the following regexp: [a-zA-Z0-9-_]+,
 * and be less than 128 bytes in length. This is reflected as part of a URL in the metadata server. Additionally,
 * to avoid ambiguity, keys must not conflict with any other metadata keys for the project.
 * <p/>
 * Value for the metadata entry: These are free-form strings, and only have meaning as interpreted by the image
 * running in the instance. The only restriction placed on values is that their size must be less than or equal to
 * 32768 bytes.
 *
 * @author David Alves
 * @see <a href="https://developers.google.com/compute/docs/reference/v1beta13/projects#resource"/>
 */
@Beta
public final class Metadata extends ForwardingMap<String, String> {

   private final Map<String, String> items;

   private Metadata(Map<String, String> items) {
      this.items = items == null ? ImmutableMap.<String, String>of() : items;
   }

   @Override
   protected Map<String, String> delegate() {
      return items;
   }

   /**
    * /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return Objects.hashCode(items);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Metadata that = Metadata.class.cast(obj);
      return equal(this.items, that.items);
   }

   /**
    * {@inheritDoc}
    */
   protected Objects.ToStringHelper string() {
      return toStringHelper(this).add("items", items);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromMetadata(this);
   }

   public final static class Builder {

      //Avoid immutable map so that we can have key replacement and removal
      private Map<String, String> items = Maps.newHashMap();

      /**
       * Adds a metadata key/value pair
       */
      public Builder put(String key, String value) {
         this.items.put(checkNotNull(key), checkNotNull(value, "value of %s", key));
         return this;
      }

      /**
       * Adds all entries
       */
      public Builder putAll(Map<String, String> map) {
         this.items.putAll(map);
         return this;
      }

      /**
       * Removes an entry from the metadata entries map.
       */
      public Builder removeItem(final String key) {
         checkNotNull(key);
         items.remove(key);
         return this;
      }

      public Metadata build() {
         return new Metadata(ImmutableMap.<String, String>builder().putAll(items).build());
      }

      public Builder fromMetadata(Metadata metadata) {
         checkNotNull(metadata);
         return new Builder().putAll(metadata);
      }
   }
}
