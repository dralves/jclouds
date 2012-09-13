package org.jclouds.nimbula.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.jclouds.javax.annotation.Nullable;

import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

public class Shape implements Comparable<Shape> {

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().fromShape(this);
    }

    public static class Builder {

        private String name;
        private float cpus;
        private int ram;
        private int io;
        private String uri;
        private String proxyuri;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cpus(float cpus) {
            this.cpus = cpus;
            return this;
        }

        public Builder ram(int ram) {
            this.ram = ram;
            return this;
        }

        public Builder io(int io) {
            this.io = io;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder proxyuri(String proxyuri) {
            this.proxyuri = proxyuri;
            return this;
        }

        public Builder fromShape(Shape shape) {
            return name(shape.name).cpus(shape.cpus).ram(shape.ram).io(shape.io)
                    .uri(shape.uri).proxyuri(shape.proxyuri);
        }

        public Shape build() {
            return new Shape(this.name, this.cpus, this.ram, this.io, this.uri,
                    this.proxyuri);
        }
    }

    private String name;
    private float cpus;
    private int ram;
    private int io;
    private String uri;
    private String proxyuri;

    @ConstructorProperties({"name", "cpus", "ram", "io", "uri", "proxyuri"})
    public Shape(String name, float cpus, int ram, int io, String uri,
                 @Nullable String proxyuri) {
        this.name = checkNotNull(name, "shape name");
        this.cpus = checkNotNull(cpus, "shape cpus");
        this.ram = checkNotNull(ram, "shape ram");
        this.io = checkNotNull(io, "shape io");
        this.uri = checkNotNull(uri, "shape uri");
        this.proxyuri = proxyuri;
    }

    public String getName() {
        return this.name;
    }

    public float getCpus() {
        return this.cpus;
    }

    public int getRam() {
        return this.ram;
    }

    public int getIo() {
        return this.io;
    }

    public String getUri() {
        return this.uri;
    }

    public String getProxyuri() {
        return this.proxyuri;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof Shape) {
            Shape that = Shape.class.cast(object);
            return Objects.equal(name, that.name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("").omitNullValues().add("name", name)
                .add("cpus", cpus).add("ram", ram).add("io", io)
                .add("uri", uri).add("proxyuri", proxyuri)
                .toString();
    }

    @Override
    public int compareTo(Shape that) {
        return ComparisonChain.start().compare(this.name, that.name).result();
    }
}
