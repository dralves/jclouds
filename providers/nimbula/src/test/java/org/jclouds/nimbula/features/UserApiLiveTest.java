package org.jclouds.nimbula.features;

import com.google.common.collect.ImmutableSet;
import org.jclouds.nimbula.domain.User;
import org.jclouds.nimbula.internal.BaseNimbulaApiLiveTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;

@Test(groups = "live", singleThreaded = true, testName = "UserApiLiveTest")
public class UserApiLiveTest extends BaseNimbulaApiLiveTest {

    private String user;
    private String password;

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
    public void testAddUser() {
        User user = new User.Builder().fullname("test user").username("test")
                .email("test@jclouds.org")
                .groups(ImmutableSet.of("cloudsoft")).build();
        UserApi api = context.getApi().getUserApi();
        api.add(user);
    }

    @Test(dependsOnMethods = "testAuthenticate")
    public void testGetUser() {
        Assert.assertNotNull(context.getApi().getUserApi()
                .get("cloudsoft/administrator"));
    }
    //
    // @Test(dependsOnMethods = "testAuthenticate")
    // public void testDiscoverUsers() {
    // Assert.assertNotNull(context.getApi().getUserApi().discover("/cloudsoft"));
    // }
}
