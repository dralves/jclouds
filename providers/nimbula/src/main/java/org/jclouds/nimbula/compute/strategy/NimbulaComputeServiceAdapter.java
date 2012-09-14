package org.jclouds.nimbula.compute.strategy;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.domain.Template;
import org.jclouds.domain.Location;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.nimbula.NimbulaClient;
import org.jclouds.nimbula.compute.predicates.InstanceIsRunningPredicate;
import org.jclouds.nimbula.domain.Instance;
import org.jclouds.nimbula.domain.LaunchPlan;
import org.jclouds.nimbula.domain.MachineImage;
import org.jclouds.nimbula.domain.Shape;
import org.jclouds.predicates.RetryablePredicate;
import org.jclouds.rest.annotations.Credential;
import org.jclouds.rest.annotations.Identity;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkState;

@Singleton
public class NimbulaComputeServiceAdapter implements ComputeServiceAdapter<Instance, Shape, MachineImage, Location> {


    // TODO allow to parameterize
    private static String PUBLIC_IMAGES_LOCATION = "/nimbula/public/";
    private static String DEFAULT_SHAPE = "large";
    private static String DEFAULT_IMAGE = "/nimbula/public/lucid64";

    private NimbulaClient client;
    private String imagesLocation;
    private RetryablePredicate<Instance> imageStatePredicate;
    private String identity;
    private String credential;


    @Inject
    public NimbulaComputeServiceAdapter(NimbulaClient client, @Identity String user, @Credential String password) {
        this.client = client;
        this.imagesLocation = PUBLIC_IMAGES_LOCATION;
        this.imageStatePredicate = new RetryablePredicate<Instance>(new InstanceIsRunningPredicate(client), 600000);
        this.identity = user;
        this.credential = password;
    }

    @Override
    public NodeAndInitialCredentials<Instance> createNodeWithGroupEncodedIntoName(String group, String name, Template template) {
        Instance instance = new Instance.Builder()
                .shape(DEFAULT_SHAPE)
                .imageList(DEFAULT_IMAGE)
                .addTags(template.getOptions().getTags())
                .label(name)
                .build();
        instance = Iterables.getOnlyElement(this.client.getInstanceApi().launch(
                new LaunchPlan.Builder().addInstance(instance).build()).getInstances());
        checkState(imageStatePredicate.apply(instance), " instance never reached RUNNING state");
        instance = getNode(instance.getName());
        return new NodeAndInitialCredentials<Instance>(instance, instance.getName(),
                LoginCredentials.builder().identity("root").password("nimbula").build());
    }

    @PostConstruct
    public void authenticate() {
        client.getUserApi().authenticate(this.identity, this.credential);
    }

    @Override
    public Iterable<Shape> listHardwareProfiles() {
        return client.getShapeApi().list("/");
    }

    @Override
    public Iterable<MachineImage> listImages() {
        return client.getMachineImageApi().list(imagesLocation);
    }

    @Override
    public MachineImage getImage(String id) {
        return client.getMachineImageApi().get(id);
    }

    @Override
    public Iterable<Location> listLocations() {
        return ImmutableSet.of();
    }

    @Override
    public Instance getNode(String id) {
        return client.getInstanceApi().get(id);
    }

    @Override
    public void destroyNode(String id) {
        client.getInstanceApi().delete(id);
    }

    @Override
    public void rebootNode(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void resumeNode(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void suspendNode(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Instance> listNodes() {
        return client.getInstanceApi().list(identity + "/");
    }
}
