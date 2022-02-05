package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.assembler.CourseAssembler;
import ar.edu.itba.paw.webapp.assembler.UserAssembler;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.NextFileNumberDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.dto.UserRegisterFormDto;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.net.URI;
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
    private CourseService courseService;

    @Autowired
    private DtoConstraintValidator dtoValidator;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private CourseAssembler courseAssembler;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response listUsers() {
        final List<User> users = userService.list();
        return Response.ok(new GenericEntity<List<UserDto>>(userAssembler.toResources(users)){}).build();
    }

    @POST
    @Consumes("application/vnd.campus.api.v1+json")
    public Response postUser(@Valid UserRegisterFormDto userRegisterForm) throws DtoValidationException {
        if(userRegisterForm == null) {
            throw new BadRequestException();
        }
        dtoValidator.validate(userRegisterForm, "Invalid Body Request");
        User user = userService.create(userRegisterForm.getFileNumber(), userRegisterForm.getName(), userRegisterForm.getSurname(),
                userRegisterForm.getUsername(), userRegisterForm.getEmail(),
                userRegisterForm.getPassword(), false);
        LOGGER.debug("User of name {} created", user.getUsername());
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + user.getUserId());
        return Response.created(location).build();
    }

    @GET
    @Path("/{userId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getUser(@PathParam("userId") Long userId) {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return Response.ok(userAssembler.toResource(user)).build();
    }

    @GET
    @Path("/{userId}/courses")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getUserCourses(@PathParam("userId") Long userId,
                                   @QueryParam("page") @DefaultValue("1") Integer page,
                                   @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        CampusPage<Course> courseCampusPage = courseService.list(userId, page, pageSize);
        if(courseCampusPage.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<CourseDto>>(courseAssembler.toResources(courseCampusPage.getContent())){});
        return PaginationBuilder.build(courseCampusPage, builder, uriInfo, pageSize);
    }

    @GET
    @Path("/{userId}/image")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getUserProfileImage(@PathParam("userId") Long userId) {
        Optional<byte[]> image = userService.getProfileImage(userId);
        if(image.isPresent()) {
            Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(image.get()));
            return response.build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/file-number/last")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getNextFileNumber() {
        return Response.ok(NextFileNumberDto.fromNextFileNumber(userService.getMaxFileNumber() + 1)).build();
    }
}
