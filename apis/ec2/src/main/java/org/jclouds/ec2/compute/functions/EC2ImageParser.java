/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.ec2.compute.functions;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.compute.util.ComputeServiceUtils.parseOsFamilyOrUnrecognized;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.collect.Memoized;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.OperatingSystemBuilder;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.compute.strategy.PopulateDefaultLoginCredentialsForImageStrategy;
import org.jclouds.compute.util.ComputeServiceUtils;
import org.jclouds.domain.Location;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.jclouds.ec2.compute.strategy.ReviseParsedImage;
import org.jclouds.ec2.domain.Image.Architecture;
import org.jclouds.ec2.domain.Image.ImageType;
import org.jclouds.logging.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

/**
 * @author Adrian Cole
 */
@Singleton
public class EC2ImageParser implements Function<org.jclouds.ec2.domain.Image, Image> {
   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final PopulateDefaultLoginCredentialsForImageStrategy credentialProvider;
   private final Supplier<Set<? extends Location>> locations;
   private final Supplier<Location> defaultLocation;
   private final Map<OsFamily, Map<String, String>> osVersionMap;
   private final ReviseParsedImage reviseParsedImage;

   @Inject
   public EC2ImageParser(PopulateDefaultLoginCredentialsForImageStrategy credentialProvider,
            Map<OsFamily, Map<String, String>> osVersionMap, @Memoized Supplier<Set<? extends Location>> locations,
            Supplier<Location> defaultLocation, ReviseParsedImage reviseParsedImage) {
      this.credentialProvider = checkNotNull(credentialProvider, "credentialProvider");
      this.locations = checkNotNull(locations, "locations");
      this.defaultLocation = checkNotNull(defaultLocation, "defaultLocation");
      this.osVersionMap = checkNotNull(osVersionMap, "osVersionMap");
      this.reviseParsedImage = checkNotNull(reviseParsedImage, "reviseParsedImage");
   }

   @Override
   public Image apply(final org.jclouds.ec2.domain.Image from) {
      if (from.getImageType() != ImageType.MACHINE) {
         return null;
      }
      ImageBuilder builder = new ImageBuilder();
      builder.providerId(from.getId());
      builder.id(from.getRegion() + "/" + from.getId());
      builder.description(from.getDescription() != null ? from.getDescription() : from.getImageLocation());
      builder.userMetadata(ImmutableMap.<String, String> of("owner", from.getImageOwnerId(), "rootDeviceType", from
               .getRootDeviceType().toString()));

      OperatingSystemBuilder osBuilder = new OperatingSystemBuilder();
      osBuilder.is64Bit(from.getArchitecture() == Architecture.X86_64);
      OsFamily family = parseOsFamilyOrUnrecognized(from.getImageLocation());
      osBuilder.family(family);
      osBuilder.version(ComputeServiceUtils.parseVersionOrReturnEmptyString(family, from.getImageLocation(),
               osVersionMap));
      osBuilder.description(from.getImageLocation());
      osBuilder.arch(from.getVirtualizationType());

      reviseParsedImage.reviseParsedImage(from, builder, family, osBuilder);

      builder.defaultCredentials(credentialProvider.execute(from));

      try {
         builder.location(Iterables.find(locations.get(), new Predicate<Location>() {

            @Override
            public boolean apply(Location input) {
               return input.getId().equals(from.getRegion());
            }

         }));
      } catch (NoSuchElementException e) {
         logger.error("unknown region %s for image %s; not in %s", from.getRegion(), from.getId(), locations);
         builder.location(new LocationBuilder().scope(LocationScope.REGION).id(from.getRegion()).description(
                  from.getRegion()).parent(defaultLocation.get()).build());
      }
      builder.operatingSystem(osBuilder.build());
      return builder.build();
   }

}
