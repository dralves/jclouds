package org.jclouds.nimbula.features;

import com.google.common.util.concurrent.ListenableFuture;
import org.jclouds.nimbula.domain.Shape;
import org.jclouds.nimbula.domain.ShapeSpec;
import org.jclouds.nimbula.handlers.CookieAuthentication;
import org.jclouds.rest.annotations.*;
import org.jclouds.rest.binders.BindToJsonPayload;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;

import javax.ws.rs.*;
import java.util.Set;

import static org.jclouds.nimbula.config.NimbulaRestClientModule.NIMBULA_DIRECTORY_MEDIA_TYPE;
import static org.jclouds.nimbula.config.NimbulaRestClientModule.NIMBULA_MEDIA_TYPE;

@SkipEncoding({'/', '='})
@RequestFilters(CookieAuthentication.class)
public interface ShapeAsyncApi {

    /**
     * @see ShapeAsyncApi#add
     */
    @POST
    @Path("/config")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<Shape>
    add(@BinderParam(BindToJsonPayload.class) ShapeSpec shapeSpec);

    /**
     * @see ShapeAsyncApi#list
     */
    @GET
    @Path("/shape{containerOrShapeName}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    @Unwrap
    @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
    public ListenableFuture<Set<Shape>> list(@PathParam("containerOrShapeName") String containerOrShapeName);

    /**
     * @see ShapeAsyncApi#get
     */
    @GET
    @Path("/shape{name}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<Shape> get(@PathParam("name") String name);

    /**
     * @see ShapeAsyncApi#discover
     */
    @GET
    @Path("/shape{container}")
    @Consumes(NIMBULA_DIRECTORY_MEDIA_TYPE)
    @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
    public ListenableFuture<Set<String>> discover(@PathParam("container") String container);

    /**
     * @see ShapeAsyncApi#delete
     */
    @DELETE
    @Consumes(NIMBULA_MEDIA_TYPE)
    @Path("/shape{name}")
    public ListenableFuture<Void> delete(@PathParam("name") String name);

}
