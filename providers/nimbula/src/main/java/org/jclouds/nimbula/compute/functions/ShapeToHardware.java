package org.jclouds.nimbula.compute.functions;

import com.google.common.base.Function;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Processor;
import org.jclouds.nimbula.domain.Shape;

public class ShapeToHardware implements Function<Shape, Hardware> {
    @Override
    public Hardware apply(Shape input) {
        return new HardwareBuilder()
                .id(input.getName())
                .processor(new Processor(input.getCpus(), 1))
                .ram(input.getRam()).build();
    }
}
