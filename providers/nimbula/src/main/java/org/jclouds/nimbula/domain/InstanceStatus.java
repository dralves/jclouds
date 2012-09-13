package org.jclouds.nimbula.domain;

public enum InstanceStatus {

    QUEUED,
    INITIALIZING,
    STARTING,
    RUNNING,
    PAUSED,
    STOPPED,
    UNREACHABLE,
    NODE_DISCONNECTED,
    ERROR,
    TERMINATED,
    UNRECOGNIZED;

    public static InstanceStatus fromValue(String v) {
        try {
            return valueOf(v.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
        }
    }
}
