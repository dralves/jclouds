package org.jclouds.nimbula.domain;

import com.google.common.collect.Sets;

import java.util.Set;

public class IFace {

    public static class Builder {

        private String name;
        private Set<String> seclists = Sets.newLinkedHashSet();
        private String nat;
        private String vethernet;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addSecurityList(String seclist) {
            this.seclists.add(seclist);
            return this;
        }

        public Builder nat(String nat) {
            this.nat = nat;
            return this;
        }

        public Builder vethernet(String vethernet) {
            this.vethernet = vethernet;
            return this;
        }

        public IFace build() {
            return new IFace(name, seclists, nat, vethernet);
        }
    }

    private String name;
    private Set<String> seclists;
    private String nat;
    private String vethernet;

    public IFace(String name, Set<String> seclists, String nat, String vethernet) {
        this.name = name;
        this.seclists = seclists;
        this.nat = nat;
        this.vethernet = vethernet;
    }

    public String getName() {
        return name;
    }

    public Set<String> getSeclists() {
        return seclists;
    }

    public String getNat() {
        return nat;
    }

    public String getVethernet() {
        return vethernet;
    }
}
