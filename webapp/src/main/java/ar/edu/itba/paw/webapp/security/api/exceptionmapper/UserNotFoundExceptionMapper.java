package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.models.exception.UserNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(UserNotFoundException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
