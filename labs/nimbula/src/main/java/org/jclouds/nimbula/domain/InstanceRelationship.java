package org.jclouds.nimbula.domain;

import com.google.common.collect.Sets;

import java.beans.ConstructorProperties;
import java.util.Set;

public class InstanceRelationship {

    public static class Builder {

        private String type;
        private Set<Instance> instances = Sets.newLinkedHashSet();

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder addInstance(Instance instance) {
            this.instances.add(instance);
            return this;
        }

        public InstanceRelationship build() {
            return new InstanceRelationship(type, instances);
        }
    }

    private String type;
    private Set<Instance> instances;

    @ConstructorProperties({"type", "instances"})
    public InstanceRelationship(String type, Set<Instance> instances) {
        this.type = type;
        this.instances = instances;
    }

    public String getType() {
        return type;
    }

    public Set<Instance> getInstances() {
        return instances;
    }
}
