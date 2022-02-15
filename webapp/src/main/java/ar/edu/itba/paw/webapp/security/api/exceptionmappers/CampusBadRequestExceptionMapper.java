package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import ar.edu.itba.paw.webapp.security.api.exceptions.CampusBadRequestException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CampusBadRequestExceptionMapper implements ExceptionMapper<CampusBadRequestException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(CampusBadRequestException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
