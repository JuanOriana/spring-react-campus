package ar.edu.itba.paw.webapp.security.api.exceptionmappers;

import ar.edu.itba.paw.models.exception.DuplicateUserException;
import ar.edu.itba.paw.webapp.security.api.models.ApiError;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@Provider
public class DuplicateUserExceptionMapper implements ExceptionMapper<DuplicateUserException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(DuplicateUserException e) {
        List<ApiError> errors = new ArrayList<>();
        if(e.isEmailDuplicated()) errors.add(new ApiError("mail", String.format("Email %s is already taken", e.getEmail())));
        if(e.isUsernameDuplicated()) errors.add(new ApiError("username", String.format("Username %s is already taken", e.getUsername())));
        if(e.isFileNumberDuplicated()) errors.add(new ApiError("file-number", String.format("File number %d is already in use", e.getFileNumber())));
        return ResponseExceptionMapperUtil.toResponse(Response.Status.CONFLICT, "Duplicated property on resource creation", uriInfo, errors);
    }
}
