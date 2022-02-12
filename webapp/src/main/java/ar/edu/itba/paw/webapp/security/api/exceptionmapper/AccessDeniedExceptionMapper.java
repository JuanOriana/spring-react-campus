package ar.edu.itba.paw.webapp.security.api.exceptionmapper;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(AccessDeniedException exception) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.FORBIDDEN,
                "You don't have enough permissions to perform this action", uriInfo);
    }
}
