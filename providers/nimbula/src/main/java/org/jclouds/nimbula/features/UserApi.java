package org.jclouds.nimbula.features;

import org.jclouds.concurrent.Timeout;
import org.jclouds.nimbula.domain.User;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface UserApi {

    public User add(User user);

    public User get(String username);

    public Set<User> list(String container);

    public Set<String> discover(String container);

    public String authenticate(String user, String password);

}
