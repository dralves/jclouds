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
package org.jclouds.rackspace.cloudloadbalancers.domain.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.rackspace.cloudloadbalancers.domain.AccessRule;
import org.jclouds.rackspace.cloudloadbalancers.domain.ConnectionThrottle;
import org.jclouds.rackspace.cloudloadbalancers.domain.HealthMonitor;
import org.jclouds.rackspace.cloudloadbalancers.domain.LoadBalancer;
import org.jclouds.rackspace.cloudloadbalancers.domain.Metadata;
import org.jclouds.rackspace.cloudloadbalancers.features.LoadBalancerApi;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @author Adrian Cole
 */
public class BaseLoadBalancer<N extends BaseNode<N>, T extends BaseLoadBalancer<N, T>> implements
      Comparable<BaseLoadBalancer<N, T>> {

   private static final String ENABLED = "enabled";
   private static final String PERSISTENCE_TYPE = "persistenceType";
   public static Algorithm[] WEIGHTED_ALGORITHMS = { Algorithm.WEIGHTED_LEAST_CONNECTIONS,
         Algorithm.WEIGHTED_ROUND_ROBIN };

   protected String name;
   protected String protocol;
   protected Integer port;
   protected SortedSet<N> nodes = ImmutableSortedSet.of(); // so tests will come out consistently
   protected Algorithm algorithm;
   protected Integer timeout;
   protected Boolean halfClosed;
   protected Map<String, SessionPersistenceType> sessionPersistence;
   protected Map<String, Boolean> connectionLogging;
   protected ConnectionThrottle connectionThrottle;
   protected HealthMonitor healthMonitor;
   protected Set<AccessRule> accessList;
   protected Set<Metadata> metadata;

   // for serialization only
   protected BaseLoadBalancer() {
   }

   public BaseLoadBalancer(String name, @Nullable String protocol, @Nullable Integer port, Iterable<N> nodes,
         @Nullable Algorithm algorithm, @Nullable Integer timeout, @Nullable Boolean halfClosed,
         @Nullable Map<String, SessionPersistenceType> sessionPersistence,
         @Nullable Map<String, Boolean> connectionLogging, @Nullable ConnectionThrottle connectionThrottle,
         @Nullable HealthMonitor healthMonitor, @Nullable Set<AccessRule> accessRules,
         @Nullable Set<Metadata> metadata) {
      this.name = checkNotNull(name, "name");
      this.protocol = protocol;// null on deleted LB
      this.port = port;// null on deleted LB
      this.nodes = ImmutableSortedSet.copyOf(checkNotNull(nodes, "nodes"));
      this.algorithm = algorithm;// null on deleted LB
      this.timeout = timeout;
      this.halfClosed = halfClosed;
      this.sessionPersistence = sessionPersistence;
      this.connectionLogging = connectionLogging;
      this.connectionThrottle = connectionThrottle;
      this.healthMonitor = healthMonitor;
      this.accessList = accessRules;
      this.metadata = metadata;
   }

   @Override
   public int compareTo(BaseLoadBalancer<N, T> arg0) {
      return name.compareTo(arg0.name);
   }

   public String getName() {
      return name;
   }

   /**
    * @return protocol, which may be null if the load balancer is deleted.
    */
   @Nullable
   public String getProtocol() {
      return protocol;
   }

   /**
    * @return port, which may be null if port has not been set.
    */
   @Nullable
   public Integer getPort() {
      return port;
   }

   public Set<N> getNodes() {
      return nodes;
   }

   /**
    * @return algorithm, which may be null if the load balancer is deleted.
    */
   @Nullable
   public Algorithm getAlgorithm() {
      return algorithm;
   }

   /**
    * @return timeout, which may be null if no timeout has been set.
    */
   @Nullable
   public Integer getTimeout() {
      return timeout;
   }

   /**
    * @return halfClosed, which may be null if halfClosed has not been set.
    */
   @Nullable
   public Boolean isHalfClosed() {
      return halfClosed;
   }

   /**
    * @return sessionPersistenceType, which may be null if sessionPersistenceType has not been set.
    */
   @Nullable
   public SessionPersistenceType getSessionPersistenceType() {
      return sessionPersistence == null ? null : sessionPersistence.get(PERSISTENCE_TYPE);
   }

   public boolean isConnectionLogging() {
      return connectionLogging == null ? false : connectionLogging.get(ENABLED);
   }

   /**
    * @return connectionThrottle, which may be null if connectionThrottle has not been set.
    */
   @Nullable
   public ConnectionThrottle getConnectionThrottle() {
      return connectionThrottle;
   }

   /**
    * @return healthMonitor, which may be null if healthMonitor has not been set.
    */
   @Nullable
   public HealthMonitor getHealthMonitor() {
      return healthMonitor;
   }

   /**
    * @return accessRules, which may be null if accessRules has not been set.
    */
   @Nullable
   public Set<AccessRule> getAccessRules() {
      return accessList;
   }

   /**
    * @return metadata, which may be null if metadata has not been set.
    */
   @Nullable
   public Set<Metadata> getMetadata() {
      return metadata;
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this).omitNullValues().add("name", name).add("protocol", protocol)
            .add("port", port).add("nodes", nodes).add("timeout", timeout).add("algorithm", algorithm)
            .add("timeout", timeout).add("sessionPersistenceType", getSessionPersistenceType())
            .add("connectionLogging", connectionLogging).add("connectionThrottle", connectionThrottle)
            .add("healthMonitor", healthMonitor).add("accessRules", accessList).add("metadata", metadata);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null || getClass() != obj.getClass())
         return false;

      LoadBalancer that = LoadBalancer.class.cast(obj);
      return Objects.equal(this.name, that.name);
   }

   /**
    * All load balancers utilize an algorithm that defines how traffic should be directed between
    * back-end nodes. The default algorithm for newly created load balancers is RANDOM, which can be
    * overridden at creation time or changed after the load balancer has been initially provisioned.
    * The algorithm name is to be constant within a major revision of the load balancing API, though
    * new algorithms may be created with a unique algorithm name within a given major revision of
    * the service API.
    */
   public static enum Algorithm {
      /**
       * The node with the lowest number of connections will receive requests.
       */
      LEAST_CONNECTIONS,
      /**
       * Back-end servers are selected at random.
       */
      RANDOM,
      /**
       * Connections are routed to each of the back-end servers in turn.
       */
      ROUND_ROBIN,
      /**
       * Each request will be assigned to a node based on the number of concurrent connections to
       * the node and its weight.
       */
      WEIGHTED_LEAST_CONNECTIONS,
      /**
       * A round robin algorithm, but with different proportions of traffic being directed to the
       * back-end nodes. Weights must be defined as part of the load balancer's node configuration.
       */
      WEIGHTED_ROUND_ROBIN, UNRECOGNIZED;

      public static Algorithm fromValue(String algorithm) {
         try {
            return valueOf(checkNotNull(algorithm, "algorithm"));
         }
         catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   /**
    * Session persistence is a feature of the load balancing service that forces multiple requests from clients to be
    * directed to the same node. This is common with many web applications that do not inherently share application
    * state between back-end servers.
    */
   public static enum SessionPersistenceType {
      /**
       * A session persistence mechanism that inserts an HTTP cookie and is used to determine the destination back-end
       * node. This is supported for HTTP load balancing only.
       */
      HTTP_COOKIE,
      /**
       * A session persistence mechanism that will keep track of the source IP address that is mapped and is able to
       * determine the destination back-end node. This is supported for HTTPS pass-through and non-HTTP load balancing
       * only.
       */
      SOURCE_IP,

      UNRECOGNIZED;

      public static SessionPersistenceType fromValue(String sessionPersistenceType) {
         try {
            return valueOf(checkNotNull(sessionPersistenceType, "sessionPersistenceType"));
         }
         catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public static class Builder<N extends BaseNode<N>, T extends BaseLoadBalancer<N, T>> {
      protected String name;
      protected String protocol;
      protected Integer port;
      protected Set<N> nodes = Sets.newLinkedHashSet();
      protected Algorithm algorithm;
      protected Integer timeout;
      protected Boolean halfClosed;
      protected Map<String, SessionPersistenceType> sessionPersistence;
      protected Map<String, Boolean> connectionLogging;
      protected ConnectionThrottle connectionThrottle;
      protected HealthMonitor healthMonitor;
      protected Set<AccessRule> accessRules;
      protected Set<Metadata> metadata;

      /**
       * Required. Name of the load balancer to create. The name must be 128 characters or less in length, and all 
       * UTF-8 characters are valid.
       */
      public Builder<N, T> name(String name) {
         this.name = checkNotNull(name, "name");
         return this;
      }

      /**
       * Required. Protocol of the service which is being load balanced.
       * 
       * @see LoadBalancerApi#listProtocols()
       */
      public Builder<N, T> protocol(String protocol) {
         this.protocol = protocol;
         return this;
      }

      /**
       * Required if the protocol being used is not in LoadBalancerApi#listProtocols() or the protocol is in 
       * LoadBalancerApi#listProtocols() but port=0. Port number for the service you are load balancing.
       */
      public Builder<N, T> port(@Nullable Integer port) {
         this.port = port;
         return this;
      }

      /**
       * Required. Nodes to be added to the load balancer.
       */
      public Builder<N, T> nodes(Iterable<N> nodes) {
         this.nodes = ImmutableSet.<N> copyOf(checkNotNull(nodes, "nodes"));
         return this;
      }

      @SuppressWarnings("unchecked")
      public Builder<N, T> node(N node) {
         this.nodes.add((N) checkNotNull(nodes, "nodes"));
         return this;
      }

      /**
       * Algorithm that defines how traffic should be directed between back-end nodes. 
       * 
       * @see Algorithm
       */
      public Builder<N, T> algorithm(@Nullable Algorithm algorithm) {
         this.algorithm = algorithm;
         return this;
      }

      /**
       * The timeout value for the load balancer and communications with its nodes. Defaults to 30 seconds with 
       * a maximum of 120 seconds.
       */
      public Builder<N, T> timeout(@Nullable Integer timeout) {
         this.timeout = timeout;
         return this;
      }

      /**
       * Enable or Disable Half-Closed support for the load balancer. Half-Closed support provides the ability 
       * for one end of the connection to terminate its output, while still receiving data from the other end. 
       * Only available for TCP/TCP_CLIENT_FIRST protocols.
       */
      public Builder<N, T> halfClosed(@Nullable Boolean halfClosed) {
         this.halfClosed = halfClosed;
         return this;
      }

      /**
       * Specifies whether multiple requests from clients are directed to the same node.
       * 
       * @see SessionPersistenceType
       */
      public Builder<N, T> sessionPersistenceType(@Nullable SessionPersistenceType sessionPersistenceType) {
         if (sessionPersistenceType != null) {
            this.sessionPersistence = Maps.newHashMap();
            this.sessionPersistence.put(PERSISTENCE_TYPE, sessionPersistenceType);
         }
         else {
            this.sessionPersistence = null;
         }

         return this;
      }

      /**
       * Current connection logging configuration. 
       */
      public Builder<N, T> connectionLogging(@Nullable Boolean connectionLogging) {
         if (connectionLogging != null) {
            this.connectionLogging = Maps.newHashMap();
            this.connectionLogging.put(ENABLED, connectionLogging);
         }
         else {
            this.connectionLogging = null;
         }

         return this;
      }

      /**
       * Specifies limits on the number of connections per IP address to help mitigate malicious or abusive 
       * traffic to your applications.
       * 
       * @see ConnectionThrottle
       */
      public Builder<N, T> connectionThrottle(@Nullable ConnectionThrottle connectionThrottle) {
         this.connectionThrottle = connectionThrottle;
         return this;
      }

      /**
       * The type of health monitor check to perform to ensure that the service is performing properly.
       * 
       * @see HealthMonitor
       */
      public Builder<N, T> healthMonitor(@Nullable HealthMonitor healthMonitor) {
         this.healthMonitor = healthMonitor;
         return this;
      }

      /**
       * The access list management feature allows fine-grained network access controls to be applied to the load 
       * balancer's virtual IP address.
       * 
       * @see AccessRule
       */
      public Builder<N, T> accessRules(@Nullable Set<AccessRule> accessRules) {
         this.accessRules = accessRules;
         return this;
      }

      /**
       * Information (metadata) that can be associated with each load balancer for the client's personal use.
       */
      public Builder<N, T> metadata(@Nullable Set<Metadata> metadata) {
         this.metadata = metadata;
         return this;
      }

      public BaseLoadBalancer<N, T> build() {
         return new BaseLoadBalancer<N, T>(name, protocol, port, nodes, algorithm, timeout, halfClosed,
               sessionPersistence, connectionLogging, connectionThrottle, healthMonitor, accessRules, metadata);
      }

      public Builder<N, T> from(T baseLB) {
         return name(baseLB.getName()).protocol(baseLB.getProtocol()).port(baseLB.getPort())
               .algorithm(baseLB.getAlgorithm()).timeout(baseLB.getTimeout()).halfClosed(baseLB.isHalfClosed())
               .nodes(baseLB.getNodes()).sessionPersistenceType(baseLB.getSessionPersistenceType())
               .connectionLogging(baseLB.isConnectionLogging()).connectionThrottle(baseLB.getConnectionThrottle())
               .healthMonitor(baseLB.getHealthMonitor()).accessRules(baseLB.getAccessRules())
               .metadata(baseLB.getMetadata());
      }
   }

   public static <N extends BaseNode<N>, T extends BaseLoadBalancer<N, T>> Builder<N, T> builder() {
      return new Builder<N, T>();
   }

   @SuppressWarnings("unchecked")
   public Builder<N, T> toBuilder() {
      return new Builder<N, T>().from((T) this);
   }
}
