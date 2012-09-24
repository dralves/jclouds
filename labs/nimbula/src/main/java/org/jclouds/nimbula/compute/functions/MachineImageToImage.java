package org.jclouds.nimbula.compute.functions;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.inject.Inject;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.domain.Location;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.nimbula.domain.MachineImage;

public class MachineImageToImage implements Function<MachineImage, Image> {

    private Supplier<Location> locationSupplier;

    @Inject
    public MachineImageToImage(Supplier<Location> locationSupplier) {
        this.locationSupplier = locationSupplier;
    }

    @Override
    public Image apply(MachineImage input) {
        return new ImageBuilder()
                .id(input.getName())
                .defaultCredentials(LoginCredentials.builder().user("root").password("nimbula").build())
                .operatingSystem(OperatingSystem.builder()
                        .family(OsFamily.UBUNTU)
                        .is64Bit(true)
                        .version("12.04")
                        .description("ubuntu").build())
                .description("ubuntu x64 uknown version")
                .status(Image.Status.AVAILABLE)
                .location(locationSupplier.get())
                .providerId("nimbula")
                .name(input.getName())
                .build();
    }
}
