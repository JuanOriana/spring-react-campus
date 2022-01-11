package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.webapp.api.model.ApiErrorDetails;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Context
    private UriInfo uriInfo;


    @Override
    public Response toResponse(NotAllowedException exception) {
        Response.Status status = Response.Status.METHOD_NOT_ALLOWED;

        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(status).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
    }
}
