package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.exception.ApiException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(ApiException exception) {
        return Response.status(exception.getStatusCode()).entity("").type(MediaType.TEXT_PLAIN).build();
    }
}