package org.jclouds.nimbula.compute.predicates;

import com.google.common.base.Predicate;
import org.jclouds.nimbula.NimbulaClient;
import org.jclouds.nimbula.domain.Instance;
import org.jclouds.nimbula.domain.InstanceStatus;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class InstanceIsRunningPredicate implements Predicate<Instance> {

    private NimbulaClient client;

    @Inject
    public InstanceIsRunningPredicate(NimbulaClient client) {
        this.client = client;
    }

    @Override
    public boolean apply(Instance input) {
        InstanceStatus currentState = client.getInstanceApi().get(input.getName()).getState();
        checkNotNull(currentState, "current state");
        switch (currentState) {
            case QUEUED:
            case INITIALIZING:
            case STARTING:
                return false;
            case RUNNING:
                return true;
            case PAUSED:
            case STOPPED:
            case UNREACHABLE:
            case NODE_DISCONNECTED:
            case ERROR:
            case TERMINATED:
            case UNRECOGNIZED:
            default:
                throw new IllegalStateException("Instance " + input.getName() + " did not reach a running state, was " + input.getState());

        }
    }
}
