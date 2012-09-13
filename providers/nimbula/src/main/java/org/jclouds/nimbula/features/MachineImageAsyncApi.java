package org.jclouds.nimbula.features;

import com.google.common.util.concurrent.ListenableFuture;
import org.jclouds.nimbula.domain.MachineImage;
import org.jclouds.nimbula.handlers.CookieAuthentication;
import org.jclouds.rest.annotations.*;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Set;

import static org.jclouds.nimbula.config.NimbulaRestClientModule.NIMBULA_DIRECTORY_MEDIA_TYPE;
import static org.jclouds.nimbula.config.NimbulaRestClientModule.NIMBULA_MEDIA_TYPE;

@SkipEncoding({'/', '='})
@RequestFilters(CookieAuthentication.class)
@Headers(keys = {"Accept-Encoding"}, values = {"gzip;q=1.0, identity; q=0.5"})
public interface MachineImageAsyncApi {

    /**
     * @see MachineImageApi#get
     */
    @GET
    @Path("/machineimage{imagename}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<MachineImage> get(@PathParam("imagename") String name);

    /**
     * @see MachineImageApi#list
     */
    @GET
    @Unwrap
    @Path("/machineimage{container}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
    public ListenableFuture<Set<MachineImage>> list(@PathParam("container") String name);

    /**
     * @see MachineImageApi#discover
     */
    @GET
    @Path("/machineimage{container}")
    @Consumes(NIMBULA_DIRECTORY_MEDIA_TYPE)
    @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
    public ListenableFuture<Set<String>> discover(@PathParam("container") String name);


}
