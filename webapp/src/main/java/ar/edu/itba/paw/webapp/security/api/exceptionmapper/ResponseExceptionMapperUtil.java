package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.webapp.security.api.model.ApiErrorDetails;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class ResponseExceptionMapperUtil {
    private ResponseExceptionMapperUtil() {
        // Util class
    }

    public static Response toResponse(Response.Status status, String message, UriInfo uriInfo) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(status).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
    }
}
