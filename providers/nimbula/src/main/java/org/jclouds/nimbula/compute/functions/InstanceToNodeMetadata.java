package org.jclouds.nimbula.compute.functions;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeMetadataBuilder;
import org.jclouds.domain.Location;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.nimbula.domain.Instance;
import org.jclouds.nimbula.domain.InstanceStatus;

import javax.inject.Inject;
import java.net.URI;
import java.util.Map;

public class InstanceToNodeMetadata implements Function<Instance, NodeMetadata> {

    private Map<InstanceStatus, NodeMetadata.Status> statusMap;
    private Supplier<Location> locationSupplier;

    @Inject
    public InstanceToNodeMetadata(Map<InstanceStatus, NodeMetadata.Status> map, Supplier<Location> locationSupplier) {
        this.statusMap = map;
        this.locationSupplier = locationSupplier;
    }

    @Override
    public NodeMetadata apply(Instance input) {
        return new NodeMetadataBuilder()
                .credentials(LoginCredentials.builder().user("root").password("nimbula").build())
                .publicAddresses(ImmutableSet.of(input.getIp()))
                .privateAddresses(ImmutableSet.of(input.getIp()))
                .status(statusMap.get(input.getState()))
                .location(locationSupplier.get())
                .backendStatus("running")
                .id(input.getName())
                .tags(input.getTags())
                .providerId("nimbula")
                .uri(URI.create(input.getUri()))
                .group(Iterables.get(input.getTags(), 0))
                .name(input.getLabel())
                .build();
    }
}
