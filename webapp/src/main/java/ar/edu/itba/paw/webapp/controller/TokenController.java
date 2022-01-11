package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.security.api.model.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.security.model.AuthenticationToken;
import ar.edu.itba.paw.webapp.security.service.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/token")
@Component
public class TokenController {
    @Context
    private UriInfo uriInfo;

    @Autowired
    private AuthenticationTokenService tokenService;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response refreshToken() {
        AuthenticationTokenDetails tokenDetails = (AuthenticationTokenDetails)
                SecurityContextHolder.getContext().getAuthentication().getDetails();

        String token = tokenService.refreshToken(tokenDetails);
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);

        return Response.ok(authenticationToken).build();
    }
}
