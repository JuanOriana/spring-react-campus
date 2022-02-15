package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import org.springframework.security.core.AuthenticationException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(AuthenticationException exception) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.UNAUTHORIZED, exception.getMessage(), uriInfo);
    }
}
