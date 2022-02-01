package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.models.exception.CourseNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CourseNotFoundExceptionMapper implements ExceptionMapper<CourseNotFoundException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(CourseNotFoundException exception) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, exception.getMessage(), uriInfo);
    }
}
