package org.jclouds.nimbula.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

public class ShapeSpec implements Comparable<ShapeSpec> {

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().fromShape(this);
    }

    public static class Builder {

        private Shape value;
        private String uri;
        private String key;

        public Builder value(Shape value) {
            this.value = value;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder fromShape(ShapeSpec shapeSpec) {
            return value(shapeSpec.value).uri(shapeSpec.uri).key(shapeSpec.key);
        }

        public ShapeSpec build() {
            return new ShapeSpec(this.value, this.uri, this.key);
        }
    }

    private Shape value;
    private String uri;
    private String key;

    @ConstructorProperties({"value", "uri", "key"})
    public ShapeSpec(Shape value, String uri, String key) {
        this.value = checkNotNull(value, "shape value");
        this.uri = checkNotNull(uri, "shape uri");
        this.key = checkNotNull(key, "shape key");
    }

    public Shape getValue() {
        return this.value;
    }

    public String getUri() {
        return this.uri;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("").omitNullValues().add("value", value)
                .add("cpus", uri).add("key", key).toString();
    }

    @Override
    public int compareTo(ShapeSpec that) {
        return ComparisonChain.start().compare(this.key, that.key).result();
    }
}
