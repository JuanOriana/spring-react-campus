package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.UserRegisterForm;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("admin")
@Component
public class AdminControllerREST {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RoleService roleService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminController.class);


//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(value = {MediaType.APPLICATION_JSON, })
//    public Response newUser(@FormDataParam("userRegister") UserRegisterForm userRegisterForm) {
//
//        //if (!validation.hasErrors()) {
//        if (userRegisterForm != null) {
//            User user = userService.create(userRegisterForm.getFileNumber(), userRegisterForm.getName(), userRegisterForm.getSurname(),
//                    userRegisterForm.getUsername(), userRegisterForm.getEmail(),
//                    userRegisterForm.getPassword(), false);
//            LOGGER.debug("User of name {} created", user.getUsername());
//            return Response.ok(new GenericEntity<User>(user) {
//            }).build();
//        }
//        //}
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }


    //TODO: ver por que con un unico @FormDataParam de tipo UserRegisterForm siempre lo recibe en null
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newUser(@FormDataParam("fileNumber") Integer fileNumber,
                            @FormDataParam("name") String name,
                            @FormDataParam("surname") String surname,
                            @FormDataParam("username") String username,
                            @FormDataParam("email") String email,
                            @FormDataParam("password") String password,
                            @FormDataParam("confirmPassword") String confirmPassword) {

        //if (!validation.hasErrors()) {
        if (fileNumber != null && name != null && surname != null && username != null && email != null && password != null && confirmPassword != null) {
            User user = userService.create(fileNumber, name, surname, username, email, password, false);
            LOGGER.debug("User of name {} created", user.getUsername());
            return Response.ok(new GenericEntity<User>(user) {
            }).build();
        }
        //}
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
