package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.common.assemblers.CourseAssembler;
import ar.edu.itba.paw.webapp.common.assemblers.RoleAssembler;
import ar.edu.itba.paw.webapp.common.assemblers.UserAssembler;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalTime;
import java.util.*;

@Path("users")
@Component
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleAssembler roleAssembler;

    @Autowired
    private MailingService mailingService;

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

    @POST
    @Path("/{userId}/mail")
    @Consumes("application/vnd.campus.api.v1+json")
    @Produces("application/vnd.campus.api.v1+json")
    public Response sendEmail(@PathParam("userId") Long userId,
                              @Valid MailFormDto mailFormDto) {
        dtoValidator.validate(mailFormDto, "Malformed body");
        User recipient = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        mailingService.sendEmail(authFacade.getCurrentUser(), recipient.getUserId(), mailFormDto.getTitle(),
                mailFormDto.getContent(), mailFormDto.getCourseId(), LocaleContextHolder.getLocale());
        return Response.accepted().build();
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
                                   @QueryParam("page-size") @DefaultValue("10") Integer pageSize) {
        CampusPage<Course> courseCampusPage = courseService.list(userId, page, pageSize);
        if(courseCampusPage.isEmpty()) {
            return Response.noContent().build();
        }
        List<Course> courses = courseCampusPage.getContent();
        List<UserCourseDto> userCourseDtoList = new ArrayList<>();
        courses.forEach(c -> {
            CourseDto courseDto = courseAssembler.toResource(c);
            RoleDto roleDto = roleAssembler.toResource(courseService.getUserRoleInCourse(c.getCourseId(), authFacade.getCurrentUserId()));
            userCourseDtoList.add(new UserCourseDto(courseDto, roleDto));
        });
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<UserCourseDto>>(userCourseDtoList){});
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

    @PUT
    @Path("/{userId}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/vnd.campus.api.v1+json")
    public Response putUserProfileImage(@PathParam("userId") Long userId,
                                        @FormDataParam("file") InputStream fileStream,
                                        @FormDataParam("file") FormDataContentDisposition fileMetadata) throws IOException {
        Optional<byte[]> image = userService.getProfileImage(userId);
        if(!image.isPresent()) {
            userService.updateProfileImage(userId, IOUtils.toByteArray(fileStream));
            return Response.created(uriInfo.getAbsolutePath()).build();
        }
        userService.updateProfileImage(userId, IOUtils.toByteArray(fileStream));
        return Response.ok().build();
    }

    @GET
    @Path("/roles")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getRoles() {
        List<Role> roles = roleService.list();
        if(roles.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(new GenericEntity<List<RoleDto>>(roleAssembler.toResources(roles)){}).build();
    }
    @GET
    @Path("/file-number/last")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getNextFileNumber() {
        return Response.ok(NextFileNumberDto.fromNextFileNumber(userService.getMaxFileNumber() + 1)).build();
    }

    private static final String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private static final String[] hours = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
            "17:00","18:00","19:00","20:00","21:00","22:00"};

    @GET
    @Path("/{userId}/timetable")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getTimeTable(@PathParam("userId") Long userId) throws JsonProcessingException {
        Map<Course, List<Timetable>> courseTimetables = new HashMap<>();

        List<Course> courses = courseService.listCurrent(userId);

        if(courses.isEmpty()) {
            return Response.noContent().build();
        }

        for (Course course: courses) {
            courseTimetables.put(course, timetableService.findById(course.getCourseId()));
        }

        ArrayList<ArrayList<CourseDto>> timeTableTest = createTimeTableMatrix(courseTimetables);
        return Response.ok(objectMapper.writeValueAsString(timeTableTest)).build();
    }

    private ArrayList<ArrayList<CourseDto>> createTimeTableMatrix(Map<Course, List<Timetable>> timeMap){
        ArrayList<ArrayList<CourseDto>> timeTableMatrix = new ArrayList<>();
        for (int i = 0; i < days.length; i++){
            timeTableMatrix.add(new ArrayList<>());
            for (int j = 0; j < hours.length; j++){
                timeTableMatrix.get(i).add(j,null);
            }
        }

        for (Map.Entry<Course,List<Timetable>> entry : timeMap.entrySet()) {
            for (Timetable timetable : entry.getValue()){
                LocalTime begins = timetable.getBegins();
                LocalTime ends = timetable.getEnd();
                for (int i = 0; i < hours.length; i++){
                    LocalTime timedHour = stringToTime(hours[i]);
                    if ((begins.isBefore(timedHour) || begins.equals(timedHour)) && ends.isAfter(timedHour)){
                        timeTableMatrix.get(timetable.getDayOfWeek()).set(i, courseAssembler.toResource(entry.getKey()));
                    }
                }
            }
        }
        return timeTableMatrix;
    }

    private static LocalTime stringToTime(String stringedTime){
        String[] tokens = stringedTime.split(":");
        return LocalTime.of(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));

    }

}
