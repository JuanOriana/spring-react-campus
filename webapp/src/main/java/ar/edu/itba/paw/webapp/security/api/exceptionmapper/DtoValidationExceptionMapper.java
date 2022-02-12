package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.webapp.dto.ExceptionDto;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DtoValidationExceptionMapper implements ExceptionMapper<DtoValidationException> {

    @Override
    public Response toResponse(final DtoValidationException exception) {
        Response.Status status = Response.Status.CONFLICT;
        return Response.status(status).entity(new ExceptionDto(exception.getMessage(), exception.getConstraintViolations(), status.getReasonPhrase(), status.getStatusCode())).build();
    }

}