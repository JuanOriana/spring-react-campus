package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.webapp.security.api.model.ApiError;
import ar.edu.itba.paw.webapp.security.api.model.ApiErrorDetails;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public class ResponseExceptionMapperUtil {
    private ResponseExceptionMapperUtil() {
        // Util class
    }

    public static Response toResponse(Response.Status status, String message, UriInfo uriInfo, List<ApiError> errorList) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());
        errorDetails.setErrors(errorList);
        return Response.status(status).entity(errorDetails).type("application/vnd.campus.api.v1+json").build();
    }

    public static Response toResponse(Response.Status status, String message, UriInfo uriInfo) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(status).entity(errorDetails).type("application/vnd.campus.api.v1+json").build();
    }
}
