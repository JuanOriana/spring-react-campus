package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import ar.edu.itba.paw.models.exception.PaginationArgumentException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PaginationArgumentExceptionMapper implements ExceptionMapper<PaginationArgumentException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(PaginationArgumentException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, "Invalid pagination parameters", uriInfo);
    }
}
