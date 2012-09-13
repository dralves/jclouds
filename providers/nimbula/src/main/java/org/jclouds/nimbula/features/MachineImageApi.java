package org.jclouds.nimbula.features;

import org.jclouds.concurrent.Timeout;
import org.jclouds.nimbula.domain.MachineImage;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface MachineImageApi {

    public MachineImage get(String name);

    public Set<MachineImage> list(String name);

    public Set<String> discover(String name);
}
