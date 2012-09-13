package org.jclouds.nimbula.features;

import org.jclouds.concurrent.Timeout;
import org.jclouds.nimbula.domain.Shape;
import org.jclouds.nimbula.domain.ShapeSpec;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface ShapeApi {

    public Shape add(ShapeSpec shapeSpec);

    public Set<Shape> list(String containerOrShapeName);

    public Shape get(String name);

    public Shape discover(String container);

    public void delete(String name);
}
