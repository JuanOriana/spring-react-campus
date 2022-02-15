package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.assemblers.UserAssembler;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/api/user")
@Component
public class ProfileController {

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private UserAssembler assembler;

    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getUser() {
        User user = authFacade.getCurrentUser();
        return Response.ok(assembler.toResource(user, true)).build();
    }
}
