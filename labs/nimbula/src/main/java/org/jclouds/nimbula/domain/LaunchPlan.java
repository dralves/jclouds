package org.jclouds.nimbula.domain;

import com.google.common.collect.Sets;

import java.beans.ConstructorProperties;
import java.util.Set;

public class LaunchPlan {

    public static class Builder {
        private Set<Instance> instances = Sets.newLinkedHashSet();
        private Set<InstanceRelationship> relationships = Sets.newLinkedHashSet();

        public Builder addInstance(Instance instance) {
            this.instances.add(instance);
            return this;
        }

        public Builder addInstanceRelationship(InstanceRelationship instanceRelationship) {
            this.relationships.add(instanceRelationship);
            return this;
        }

        public LaunchPlan build() {
            return new LaunchPlan(instances, relationships);
        }
    }

    private Set<Instance> instances;
    private Set<InstanceRelationship> relationships;

    @ConstructorProperties({"instances", "relationships"})
    public LaunchPlan(Set<Instance> instances, Set<InstanceRelationship> relationships) {
        this.instances = instances;
        this.relationships = relationships;
    }

    public Set<Instance> getInstances() {
        return instances;
    }

    public Set<InstanceRelationship> getRelationships() {
        return relationships;
    }
}
