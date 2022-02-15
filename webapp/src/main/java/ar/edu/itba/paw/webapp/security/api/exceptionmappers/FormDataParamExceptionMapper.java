package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import org.glassfish.jersey.media.multipart.FormDataParamException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FormDataParamExceptionMapper implements ExceptionMapper<FormDataParamException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(FormDataParamException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST,
                "Malformed form data param", uriInfo);
    }
}
