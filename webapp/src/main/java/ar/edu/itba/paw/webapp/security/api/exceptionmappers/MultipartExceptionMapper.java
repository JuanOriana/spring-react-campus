package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import org.springframework.web.multipart.MultipartException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MultipartExceptionMapper implements ExceptionMapper<MultipartException> {
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(MultipartException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
