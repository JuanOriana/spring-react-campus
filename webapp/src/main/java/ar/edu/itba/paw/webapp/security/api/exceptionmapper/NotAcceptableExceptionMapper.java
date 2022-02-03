package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAcceptableExceptionMapper implements ExceptionMapper<NotAcceptableException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotAcceptableException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NOT_ACCEPTABLE, "Provided api version on contract is not acceptable", uriInfo);
    }
}
