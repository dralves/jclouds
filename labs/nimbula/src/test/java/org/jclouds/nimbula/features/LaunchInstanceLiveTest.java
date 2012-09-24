package org.jclouds.nimbula.features;

import com.google.common.collect.Iterables;
import org.jclouds.nimbula.compute.predicates.InstanceIsRunningPredicate;
import org.jclouds.nimbula.domain.*;
import org.jclouds.nimbula.internal.BaseNimbulaApiLiveTest;
import org.jclouds.predicates.RetryablePredicate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Properties;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.Assert.*;

/**
 * Test everything required to be able to launch a cluster on nimbula, should be separated into separate tests later.
 */
@Test(groups = "live", singleThreaded = true, testName = "UserApiLiveTest")
public class LaunchInstanceLiveTest extends BaseNimbulaApiLiveTest {

    private String user;
    private String password;
    private Shape largeShape;
    private ImageList ubuntuImageList;

    @BeforeClass
    @Override
    public Properties setupProperties() {
        user = checkNotNull(System.getProperty("nimbula.identity"));
        password = checkNotNull(System.getProperty("nimbula.credential"));
        Properties props = super.setupProperties();
        props.put("nimbula.identity", user);
        props.put("nimbula.credential", password);
        return props;
    }

    public void testAuthenticate() {
        UserApi api = context.getApi().getUserApi();
        // no exception means auth worked
        api.authenticate(user, password);
    }

    @Test(dependsOnMethods = "testAuthenticate")
    public void testListAndGetShapes() {
        ShapeApi api = context.getApi().getShapeApi();
        Set<Shape> shapes = api.list("/");
        assertFalse(shapes.isEmpty());
        for (Shape shape : shapes) {
            assertNotNull(api.get("/" + shape.getName()));
            if (shape.getName().equals("large")) {
                largeShape = shape;

            }
        }
        assertNotNull(largeShape);
    }

    @Test(dependsOnMethods = "testAuthenticate")
    public void testListAndGetMachineImages() {
        MachineImageApi api = context.getApi().getMachineImageApi();
        Set<MachineImage> machineImages = api.list("/nimbula/public/");
        assertFalse(machineImages.isEmpty());
        for (MachineImage machineImage : machineImages) {
            assertNotNull(api.get(machineImage.getName()));

        }
    }

    @Test(dependsOnMethods = "testAuthenticate")
    public void testListAndGetImageLists() {
        ImageListApi api = context.getApi().getImageListApi();
        Set<ImageList> images = api.list("/nimbula/public/");
        assertFalse(images.isEmpty());
        int counter = 0;
        for (ImageList image : images) {
            assertNotNull(api.get(image.getName()));
            if (image.getName().equals("/nimbula/public/lucid64")) {
                ubuntuImageList = image;
            }
        }
        assertNotNull(ubuntuImageList);
    }

    @Test(dependsOnMethods = {"testListAndGetShapes", "testListAndGetMachineImages", "testListAndGetImageLists"})
    public void testLaunchInstance() {
        LaunchPlan plan = new LaunchPlan.Builder().addInstance(new Instance.Builder().imageList(ubuntuImageList.getName()).shape(largeShape.getName()).build()).build();
        InstanceApi api = context.getApi().getInstanceApi();
        plan = api.launch(plan);
        Instance instance = Iterables.getOnlyElement(plan.getInstances());
        assertTrue(new RetryablePredicate<Instance>(new InstanceIsRunningPredicate(context.getApi()), 600000).apply(instance));
        System.out.println(instance);
        instance = Iterables.getOnlyElement(context.getApi().getInstanceApi().list(instance.getName() + "/"));
        assertNotNull(instance);
        context.getApi().getInstanceApi().delete(instance.getName());
    }

}
