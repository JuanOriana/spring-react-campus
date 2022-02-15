package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(JsonProcessingException exception) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST,
                "Malformed JSON for given metadata", uriInfo);

    }
}
