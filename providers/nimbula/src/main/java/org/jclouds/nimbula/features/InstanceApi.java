package org.jclouds.nimbula.features;

import org.jclouds.concurrent.Timeout;
import org.jclouds.nimbula.domain.Instance;
import org.jclouds.nimbula.domain.LaunchPlan;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface InstanceApi {

    public LaunchPlan launch(LaunchPlan plan);

    public Set<Instance> list(String container);

    public Instance get(String name);

    public void delete(String name);
}
