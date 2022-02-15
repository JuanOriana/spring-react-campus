package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import ar.edu.itba.paw.models.exception.UserEnrolledException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserEnrolledExceptionMapper implements ExceptionMapper<UserEnrolledException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(UserEnrolledException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.CONFLICT, e.getMessage(), uriInfo);
    }
}
