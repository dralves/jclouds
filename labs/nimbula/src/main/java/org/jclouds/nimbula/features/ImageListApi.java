package org.jclouds.nimbula.features;

import org.jclouds.concurrent.Timeout;
import org.jclouds.nimbula.domain.ImageList;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface ImageListApi {

    public ImageList get(String name);

    public Set<ImageList> list(String name);

    public Set<String> discover(String name);

    public void delete(String name);
}
