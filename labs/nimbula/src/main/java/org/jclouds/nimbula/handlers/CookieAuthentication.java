package org.jclouds.nimbula.handlers;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.inject.Singleton;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.http.HttpResponse;
import org.jclouds.rest.AuthorizationException;

import javax.ws.rs.core.HttpHeaders;

/**
 * The authenticator for requests. Also handles getting the cookie from the
 * authenticate call.
 */
@Singleton
public class CookieAuthentication implements Function<HttpResponse, String>,
        HttpRequestFilter {

    private String cookie;

    @Override
    public String apply(HttpResponse input) {
        switch (input.getStatusCode()) {
            // success with no content
            case 204:
                this.cookie = Iterables.getOnlyElement(input.getHeaders().get(
                        HttpHeaders.SET_COOKIE));
                return this.cookie;
            default:
                throw new AuthorizationException();
        }
    }

    @Override
    public HttpRequest filter(HttpRequest request) throws HttpException {
        if (cookie != null) {
            return request.toBuilder().replaceHeader(HttpHeaders.COOKIE, cookie)
                    .build();
        }
        return request;
    }
}
