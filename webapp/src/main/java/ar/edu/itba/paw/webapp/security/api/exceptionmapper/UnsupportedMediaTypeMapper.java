package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnsupportedMediaTypeMapper implements ExceptionMapper<NotSupportedException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotSupportedException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), uriInfo);
    }
}
