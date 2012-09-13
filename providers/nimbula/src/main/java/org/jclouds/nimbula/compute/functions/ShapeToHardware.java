package org.jclouds.nimbula.compute.functions;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.inject.Inject;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Processor;
import org.jclouds.domain.Location;
import org.jclouds.nimbula.domain.Shape;

public class ShapeToHardware implements Function<Shape, Hardware> {

    private Supplier<Location> locationSupplier;

    @Inject
    public ShapeToHardware(Supplier<Location> locationSupplier) {
        this.locationSupplier = locationSupplier;
    }

    @Override
    public Hardware apply(Shape input) {
        return new HardwareBuilder()
                .id(input.getName())
                .processor(new Processor(input.getCpus(), 1))
                .ram(input.getRam())
                .location(locationSupplier.get())
                .providerId("nimbula")
                .build();
    }
}
