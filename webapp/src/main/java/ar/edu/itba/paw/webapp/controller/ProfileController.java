package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.assembler.UserAssembler;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("user")
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
        return Response.ok(new GenericEntity<UserDto>(assembler.toResource(user)){}).build();
    }
}
