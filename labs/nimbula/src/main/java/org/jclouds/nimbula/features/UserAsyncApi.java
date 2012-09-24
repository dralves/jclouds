package org.jclouds.nimbula.features;

import com.google.common.util.concurrent.ListenableFuture;
import org.jclouds.nimbula.domain.User;
import org.jclouds.nimbula.handlers.CookieAuthentication;
import org.jclouds.rest.annotations.*;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.ws.rs.*;
import java.util.Set;

import static org.jclouds.nimbula.config.NimbulaRestClientModule.NIMBULA_DIRECTORY_MEDIA_TYPE;
import static org.jclouds.nimbula.config.NimbulaRestClientModule.NIMBULA_MEDIA_TYPE;

@RequestFilters(CookieAuthentication.class)
@Headers(keys = {"Accept-Encoding"}, values = {"gzip;q=1.0, identity; q=0.5"})
public interface UserAsyncApi {

    /**
     * @see UserApi#authenticate
     */
    @POST
    @Path("/authenticate/")
    @Produces(NIMBULA_MEDIA_TYPE)
    @Consumes(NIMBULA_MEDIA_TYPE)
    @MapBinder(BindToJsonPayload.class)
    @ResponseParser(CookieAuthentication.class)
    public ListenableFuture<Void> authenticate(@PayloadParam("user") String user, @PayloadParam("password") String password);

    /**
     * @see UserApi#add
     */
    @POST
    @Path("/user/")
    @Consumes(NIMBULA_MEDIA_TYPE)
    @Produces(NIMBULA_MEDIA_TYPE)
    @Unwrap
    public ListenableFuture<User> add(@BinderParam(BindToJsonPayload.class) User user);

    /**
     * @see UserApi#get
     */
    @GET
    @Path("/user{user}")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<User> get(@PathParam("user") String username);

    /**
     * @see UserApi#list
     */
    @GET
    @Path("/user{container}/")
    @Consumes(NIMBULA_MEDIA_TYPE)
    public ListenableFuture<Set<User>> list(@PathParam("container") String container);

    /**
     * @see UserApi#discover
     */
    @GET
    @Path("/user{container}/")
    @Consumes(NIMBULA_DIRECTORY_MEDIA_TYPE)
    public ListenableFuture<Set<String>> discover(@PathParam("container") String container);

    /**
     * @see UserApi#discover
     */
    @DELETE
    @Path("/user{container}/")
    @Consumes(NIMBULA_DIRECTORY_MEDIA_TYPE)
    public ListenableFuture<Void> delete(String name);
}
