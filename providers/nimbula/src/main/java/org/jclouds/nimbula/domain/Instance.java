package org.jclouds.nimbula.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Instance {

    public static class Builder {

        private String account;
        private Map<String, IFace> networking = Maps.newLinkedHashMap();
        private String name;
        private Set<String> placementRequirements = Sets.newLinkedHashSet();
        private Set<String> tags = Sets.newLinkedHashSet();
        private String ip;
        private String startTime;
        private Set<String> storageAttachments = Sets.newLinkedHashSet();
        private InstanceStatus state;
        private String diskAttach;
        private String label;
        private List<String> bootOrder = Lists.newArrayList();
        private String shape;
        private String proxyUri;
        private Map<String, Object> attributes = Maps.newLinkedHashMap();
        private String site;
        private int entry = 1;
        private String imageList;
        private String uri;
        private String vnc;

        public Builder account(String account) {
            this.account = account;
            return this;
        }

        public Builder addIface(String name, IFace iface) {
            this.networking.put(name, iface);
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addPlacementRequirement(String placementRequirement) {
            this.placementRequirements.add(placementRequirement);
            return this;
        }

        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder startTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder addStorageAttachment(String storageAttachment) {
            this.storageAttachments.add(storageAttachment);
            return this;
        }

        public Builder state(InstanceStatus state) {
            this.state = state;
            return this;
        }

        public Builder diskAttach(String diskAttach) {
            this.diskAttach = diskAttach;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder addBootOrder(String bootOrder) {
            this.bootOrder.add(bootOrder);
            return this;
        }

        public Builder shape(String shape) {
            this.shape = shape;
            return this;
        }

        public Builder proxiUri(String proxyUri) {
            this.proxyUri = proxyUri;
            return this;
        }

        public Builder addAttribute(String name, Object value) {
            this.attributes.put(name, value);
            return this;
        }

        public Builder site(String site) {
            this.site = site;
            return this;
        }

        public Builder entry(int entry) {
            this.entry = entry;
            return this;
        }

        public Builder imageList(String imageList) {
            this.imageList = imageList;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder vnc(String vnc) {
            this.vnc = vnc;
            return this;
        }

        public Instance build() {
            return new Instance(account, networking, name, placementRequirements, tags, ip, startTime, storageAttachments, state, diskAttach, label, bootOrder, shape, proxyUri, attributes, site, entry, imageList, uri, vnc);
        }


    }

    private String account;
    private Map<String, IFace> networking;
    private String name;
    private Set<String> placementRequirements;
    private Set<String> tags;
    private String ip;
    private String startTime;
    private Set<String> storageAttachments;
    private InstanceStatus state;
    private String diskAttach;
    private String label;
    private List<String> bootOrder;
    private String shape;
    private String proxyUri;
    private Map<String, Object> attributes;
    private String site;
    private int entry;
    private String imagelist;
    private String uri;
    private String vnc;


    @ConstructorProperties({"account", "networking", "name", "placementRequirements", "tags", "ip", "startTime",
            "storageAttachments", "state", "diskAttach", "label", "bootOrder", "shape", "proxyUri", "attributes",
            "site", "entry", "imagelist", "uri", "vnc"})
    public Instance(String account, Map<String, IFace> networking, String name, Set<String> placementRequirements,
                    Set<String> tags, String ip, String startTime, Set<String> storageAttachments, InstanceStatus state,
                    String diskAttach, String label, List<String> bootOrder, String shape, String proxyUri,
                    Map<String, Object> attributes, String site, int entry, String imagelist, String uri, String vnc) {
        this.account = account;
        this.networking = networking;
        this.name = name;
        this.placementRequirements = placementRequirements;
        this.tags = tags;
        this.ip = ip;
        this.startTime = startTime;
        this.storageAttachments = storageAttachments;
        this.state = state;
        this.diskAttach = diskAttach;
        this.label = label;
        this.bootOrder = bootOrder;
        this.shape = shape;
        this.proxyUri = proxyUri;
        this.attributes = attributes;
        this.site = site;
        this.entry = entry;
        this.imagelist = imagelist;
        this.uri = uri;
        this.vnc = vnc;
    }

    public String getAccount() {
        return account;
    }

    public Map<String, IFace> getNetworking() {
        return networking;
    }

    public String getName() {
        return name;
    }

    public Set<String> getPlacementRequirements() {
        return placementRequirements;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getIp() {
        return ip;
    }

    public String getStartTime() {
        return startTime;
    }

    public Set<String> getStorageAttachments() {
        return storageAttachments;
    }

    public InstanceStatus getState() {
        return state;
    }

    public String getDiskAttach() {
        return diskAttach;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getBootOrder() {
        return bootOrder;
    }

    public String getShape() {
        return shape;
    }

    public String getProxyUri() {
        return proxyUri;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getSite() {
        return site;
    }

    public int getEntry() {
        return entry;
    }

    public String getImagelist() {
        return imagelist;
    }

    public String getUri() {
        return uri;
    }

    public String getVnc() {
        return vnc;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "account='" + account + '\'' +
                ", networking=" + networking +
                ", name='" + name + '\'' +
                ", placementRequirements=" + placementRequirements +
                ", tags=" + tags +
                ", ip='" + ip + '\'' +
                ", startTime='" + startTime + '\'' +
                ", storageAttachments=" + storageAttachments +
                ", state=" + state +
                ", diskAttach='" + diskAttach + '\'' +
                ", label='" + label + '\'' +
                ", bootOrder=" + bootOrder +
                ", shape='" + shape + '\'' +
                ", proxyUri='" + proxyUri + '\'' +
                ", attributes=" + attributes +
                ", site='" + site + '\'' +
                ", entry=" + entry +
                ", imagelist='" + imagelist + '\'' +
                ", uri='" + uri + '\'' +
                ", vnc='" + vnc + '\'' +
                '}';
    }
}
