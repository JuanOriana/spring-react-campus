package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.NextFileNumberDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.dto.UserRegisterFormDto;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Path("users")
@Component
public class UserController {
    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private DtoConstraintValidator dtoValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response listUsers() {
        final List<User> allUsers = userService.list();
        return Response.ok(new GenericEntity<List<User>>(allUsers) {}).build();
    }

    @GET
    @Path("/{userId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getUser(@PathParam("userId")Long userId){
        Long currentUserId = authFacade.getCurrentUserId();

        if(authFacade.isAdminUser() || currentUserId.equals(userId)){
            User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
            return Response.ok(UserDto.fromUser(user)).build();
        }else{
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @GET
    @Path("/{userId}/profile-image")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getUserProfileImage(@PathParam("userId")Long userId){
        Optional<byte[]> image = userService.getProfileImage(userId);

        if(image.isPresent()) {
            Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(image.get()));
            return response.build();
        }

        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newUser(@Valid UserRegisterFormDto userRegisterForm) throws DtoValidationException {

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }


        if (userRegisterForm != null) {
            dtoValidator.validate(userRegisterForm, "Failed to validate new user attributes");
            User user = userService.create(userRegisterForm.getFileNumber(), userRegisterForm.getName(), userRegisterForm.getSurname(),
                    userRegisterForm.getUsername(), userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(), false);
            LOGGER.debug("User of name {} created", user.getUsername());
            return Response.ok(UserDto.fromUser(user)).status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/max-file-number")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getNextFileNumber(){

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(NextFileNumberDto.fromNextFileNumber(userService.getMaxFileNumber() + 1)).build();
    }
}
