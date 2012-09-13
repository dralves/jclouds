package org.jclouds.nimbula.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.beans.ConstructorProperties;
import java.util.Set;

public class User implements Comparable<User> {

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().fromUser(this);
    }

    public static class Builder {
        String username;
        String fullname;
        String email;
        String password;
        String uri;
        Set<String> groups;
        boolean blacklisted;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder groups(Set<String> groups) {
            this.groups = groups;
            return this;
        }

        public Builder blacklisted(boolean blacklisted) {
            this.blacklisted = blacklisted;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder fromUser(User user) {
            return username(user.getUsername()).fullname(user.getFullname())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .uri(user.getUri()).groups(user.getGroups())
                    .blacklisted(user.isBlacklisted());
        }

        public User build() {
            return new User(username, fullname, email, password, uri, groups,
                    blacklisted);
        }
    }

    String username;
    String fullname;
    String email;
    String password;
    String uri;
    Set<String> groups;
    boolean blacklisted;

    @ConstructorProperties({"username", "fullname", "email", "password", "uri",
            "groups", "blacklisted"})
    public User(String username, String fullname, String email, String password,
                String uri, Set<String> groups, boolean blacklisted) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.uri = uri;
        this.groups = groups;
        this.blacklisted = blacklisted;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUri() {
        return uri;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof User) {
            User that = User.class.cast(object);
            return Objects.equal(username, username);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("").omitNullValues()
                .add("username", username)
                .add("fullname", fullname).add("email", email)
                .add("groups", groups)
                .add("blacklisted", blacklisted).toString();
    }

    @Override
    public int compareTo(User that) {
        return ComparisonChain.start().compare(this.username, that.username)
                .result();
    }
}
