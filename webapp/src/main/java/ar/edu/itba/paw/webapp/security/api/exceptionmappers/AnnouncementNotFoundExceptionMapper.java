package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import ar.edu.itba.paw.models.exception.AnnouncementNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class AnnouncementNotFoundExceptionMapper implements ExceptionMapper<AnnouncementNotFoundException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(AnnouncementNotFoundException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
