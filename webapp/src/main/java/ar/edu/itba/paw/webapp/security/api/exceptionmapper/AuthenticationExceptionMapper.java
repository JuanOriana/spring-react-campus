package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

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
        return ResponseExceptionMapperUtil.toResponse(Response.Status.FORBIDDEN, exception.getMessage(), uriInfo);
    }
}
