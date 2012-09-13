package org.jclouds.nimbula.domain;

import java.beans.ConstructorProperties;


public class ImageList {

    public static class Builder {

        int defaultImage;
        String name;
        String description;
        String account;

        public Builder defaultImage(int index) {
            this.defaultImage = defaultImage;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder account(String account) {
            this.account = account;
            return this;
        }

        public ImageList build() {
            return new ImageList(defaultImage, name, description, account);
        }
    }

    int defaultImage;
    String name;
    String description;
    String account;

    @ConstructorProperties({"defaultImage", "name", "description", "account"})
    public ImageList(int defaultImage, String name, String description,
                     String account) {
        this.defaultImage = defaultImage;
        this.name = name;
        this.description = description;
        this.account = account;
    }

    public int getDefaultImage() {
        return defaultImage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAccount() {
        return account;
    }
}
