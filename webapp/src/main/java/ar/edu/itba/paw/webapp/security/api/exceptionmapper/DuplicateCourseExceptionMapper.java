package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import ar.edu.itba.paw.models.exception.DuplicateCourseException;
import ar.edu.itba.paw.webapp.security.api.model.ApiError;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@Provider
public class DuplicateCourseExceptionMapper implements ExceptionMapper<DuplicateCourseException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(DuplicateCourseException e) {
        List<ApiError> errors = new ArrayList<>();
        errors.add(new ApiError("board, quarter, year, subjectId", "A course with these properties already exists"));
        return ResponseExceptionMapperUtil.toResponse(Response.Status.CONFLICT, "Duplicated property on resource creation", uriInfo, errors);
    }
}
