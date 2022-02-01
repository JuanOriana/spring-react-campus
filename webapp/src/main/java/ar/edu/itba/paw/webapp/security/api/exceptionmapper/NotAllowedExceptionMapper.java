package ar.edu.itba.paw.webapp.security.api.exceptionmapper;


import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Context
    private UriInfo uriInfo;


    @Override
    public Response toResponse(NotAllowedException exception) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.METHOD_NOT_ALLOWED, exception.getMessage(), uriInfo);
    }
}
