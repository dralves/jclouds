package org.jclouds.nimbula.domain;

import com.google.common.collect.Maps;

import java.beans.ConstructorProperties;
import java.util.Map;

public class MachineImage {

    public static class Builder {

        Map<String, String> attributes = Maps.newHashMap();
        String name;
        String account;
        String uri;
        String file;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder account(String account) {
            this.account = account;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder file(String file) {
            this.file = file;
            return this;
        }

        public Builder addAttribute(String name, String value) {
            this.attributes.put(name, value);
            return this;
        }

        public MachineImage build() {
            return new MachineImage(name, account, uri, file, this.attributes);
        }
    }

    Map<String, String> attributes;
    String name;
    String account;
    String uri;
    String file;

    @ConstructorProperties({"name", "account", "uri", "file", "attributes"})
    public MachineImage(String name, String account, String uri, String file,
                        Map<String, String> attributes) {
        this.attributes = attributes;
        this.name = name;
        this.account = account;
        this.uri = uri;
        this.file = file;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getUri() {
        return uri;
    }

    public String getFile() {
        return file;
    }
}
