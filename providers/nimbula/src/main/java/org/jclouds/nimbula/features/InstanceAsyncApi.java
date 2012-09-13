package org.jclouds.nimbula.features;

import com.google.common.util.concurrent.ListenableFuture;
import org.jclouds.nimbula.domain.Instance;
import org.jclouds.nimbula.domain.LaunchPlan;
import org.jclouds.nimbula.handlers.CookieAuthentication;
import org.jclouds.rest.annotations.*;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.ws.rs.*;
import java.util.Set;

import static org.jclouds.nimbula.config.NimbulaRestClientModule.NIMBULA_MEDIA_TYPE;

@SkipEncoding({'/', '='})
@RequestFilters(CookieAuthentication.class)
@Headers(keys = {"Accept-Encoding"}, values = {"gzip;q=1.0, identity; q=0.5"})
public interface InstanceAsyncApi {

    @POST
    @Consumes(NIMBULA_MEDIA_TYPE)
    @Produces(NIMBULA_MEDIA_TYPE)
    @Path("/launchplan/")
    public ListenableFuture<LaunchPlan> launch(@BinderParam(BindToJsonPayload.class) LaunchPlan plan);

    /**
     * @see InstanceApi#list
     */
    @GET
    @Unwrap
    @Path("/instance{container}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<Set<Instance>> list(@PathParam("container") String container);

    /**
     * @see InstanceApi#get
     */
    @GET
    @Path("/instance{name}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<Instance> get(@PathParam("name") String name);


    /**
     * @see InstanceApi#delete
     */
    @DELETE
    @Path("/instance{name}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<Void> delete(@PathParam("name") String name);

}
