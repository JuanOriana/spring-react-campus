package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import org.glassfish.jersey.server.ParamException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PathParamExceptionMapper implements ExceptionMapper<ParamException.PathParamException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ParamException.PathParamException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
