package ar.edu.itba.paw.webapp.security.api.exceptionmapper;

import org.glassfish.jersey.server.ParamException.QueryParamException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class QueryParamExceptionMapper implements ExceptionMapper<QueryParamException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(QueryParamException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, "Malformed query param", uriInfo);
    }
}
