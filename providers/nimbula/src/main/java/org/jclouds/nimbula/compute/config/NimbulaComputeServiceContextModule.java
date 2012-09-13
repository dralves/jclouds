package org.jclouds.nimbula.compute.config;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.config.ComputeServiceAdapterContextModule;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.domain.Location;
import org.jclouds.functions.IdentityFunction;
import org.jclouds.nimbula.compute.functions.InstanceToNodeMetadata;
import org.jclouds.nimbula.compute.functions.MachineImageToImage;
import org.jclouds.nimbula.compute.functions.ShapeToHardware;
import org.jclouds.nimbula.compute.strategy.NimbulaComputeServiceAdapter;
import org.jclouds.nimbula.domain.Instance;
import org.jclouds.nimbula.domain.InstanceStatus;
import org.jclouds.nimbula.domain.MachineImage;
import org.jclouds.nimbula.domain.Shape;

import javax.inject.Singleton;
import java.util.Map;

public class NimbulaComputeServiceContextModule extends
        ComputeServiceAdapterContextModule<Instance, Shape, MachineImage, Location> {

    protected void configure() {
        super.configure();
        bind(new TypeLiteral<ComputeServiceAdapter<Instance, Shape, MachineImage, Location>>() {
        }).to(NimbulaComputeServiceAdapter.class);
        bind(new TypeLiteral<Function<Instance, NodeMetadata>>() {
        }).to(InstanceToNodeMetadata.class);
        bind(new TypeLiteral<Function<Shape, Hardware>>() {
        }).to(ShapeToHardware.class);
        bind(new TypeLiteral<Function<MachineImage, Image>>() {
        }).to(MachineImageToImage.class);
        bind(new TypeLiteral<Function<Location, Location>>() {
        }).to(Class.class.cast(IdentityFunction.class));
    }

    @VisibleForTesting
    public static final Map<InstanceStatus, NodeMetadata.Status> toPortableNodeStatus = ImmutableMap
            .<InstanceStatus, NodeMetadata.Status>builder()
            .put(InstanceStatus.QUEUED, NodeMetadata.Status.PENDING)//
            .put(InstanceStatus.INITIALIZING, NodeMetadata.Status.PENDING)//
            .put(InstanceStatus.STARTING, NodeMetadata.Status.PENDING)//
            .put(InstanceStatus.RUNNING, NodeMetadata.Status.RUNNING)//
            .put(InstanceStatus.PAUSED, NodeMetadata.Status.SUSPENDED)//
            .put(InstanceStatus.STOPPED, NodeMetadata.Status.SUSPENDED)//
            .put(InstanceStatus.UNREACHABLE, NodeMetadata.Status.ERROR)//
            .put(InstanceStatus.NODE_DISCONNECTED, NodeMetadata.Status.ERROR)//
            .put(InstanceStatus.ERROR, NodeMetadata.Status.ERROR)//
            .put(InstanceStatus.TERMINATED, NodeMetadata.Status.TERMINATED)//
            .put(InstanceStatus.UNRECOGNIZED, NodeMetadata.Status.UNRECOGNIZED).build();

    @Singleton
    @Provides
    Map<InstanceStatus, NodeMetadata.Status> toPortableNodeStatus() {
        return toPortableNodeStatus;
    }


}
