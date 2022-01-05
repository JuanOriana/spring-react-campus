package ar.edu.itba.paw.webapp.mappers;

import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class ServerErrorMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if(exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity("").type(MediaType.TEXT_PLAIN).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("").type(MediaType.TEXT_PLAIN).build();
    }
}
