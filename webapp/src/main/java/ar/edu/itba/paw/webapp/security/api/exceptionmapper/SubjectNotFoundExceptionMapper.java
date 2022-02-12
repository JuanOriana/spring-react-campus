package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.models.exception.SubjectNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class SubjectNotFoundExceptionMapper implements ExceptionMapper<SubjectNotFoundException> {
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(SubjectNotFoundException exception) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, exception.getMessage(), uriInfo);
    }
}
