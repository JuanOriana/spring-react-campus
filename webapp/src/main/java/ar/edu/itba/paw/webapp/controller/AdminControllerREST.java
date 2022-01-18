package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.form.UserToCourseForm;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("admin")
@Component
@ComponentScan({"ar.edu.itba.paw.webapp.constraint.validator"})
public class AdminControllerREST {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private DtoConstraintValidator DtoValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RoleService roleService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    //TODO: pasar cada metodo a su controller correspondiente

    private boolean isAdminUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CampusUser) {
            return ((CampusUser)principal).isAdmin();
        } else {
            return false;
        }
    }

    @GET
    @Path("/user/new")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getNextFileNumber(){

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(NextFileNumberDto.fromNextFileNumber(userService.getMaxFileNumber() + 1)).build();
    }

    @POST
    @Path("/user/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newUser(@Valid UserRegisterFormDto userRegisterForm) throws DtoValidationException {

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (userRegisterForm != null) {
            DtoValidator.validate(userRegisterForm, "Failed to validate new user attributes");
            User user = userService.create(userRegisterForm.getFileNumber(), userRegisterForm.getName(), userRegisterForm.getSurname(),
                    userRegisterForm.getUsername(), userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(), false);
            LOGGER.debug("User of name {} created", user.getUsername());
            return Response.ok(UserDto.fromUser(user)).status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/subjects")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getSubjects(){

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok( new GenericEntity<List<SubjectDto>>(subjectService.list().stream().map(SubjectDto::fromSubject).collect(Collectors.toList())){}).build();
    }

    @POST
    @Path("/course/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newCourse(@Valid CourseFormDto courseForm) throws DtoValidationException, URISyntaxException { //TODO: ver bien como seria la parte del json para startTimes y endTimes

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(courseForm != null) {
            Course course = courseService.create(courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard()
                    , courseForm.getSubjectId(), courseForm.getStartTimes(), courseForm.getEndTimes());
            LOGGER.debug("Created course in year {} in quarter {} of subjectId {} with id {}", courseForm.getYear(),
                    courseForm.getBoard(), courseForm.getSubjectId(), course.getCourseId());
            URI enrollUsers = new URI(uriInfo.getBaseUri().normalize().toString() + "admin/course/" + course.getCourseId() + "/enroll"); //TODO: rev si termina quedando asi
            return Response.seeOther(enrollUsers).status(Response.Status.SEE_OTHER).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/courses")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourses(@QueryParam("year") Integer year, @QueryParam("quarter") Integer quarter){

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<Course> courses;
        List<Integer> availableYears = courseService.getAvailableYears();

        if (year == null && quarter == null){
            courses = courseService.list();
            courses.sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());
            return Response.ok( AllCoursesResponseDto.responseFrom(availableYears, courses)).build();
        }else {
            if (year == null){
                year = Calendar.getInstance().get(Calendar.YEAR);
            }
            if (quarter == null){
                if (Calendar.getInstance().get(Calendar.MONTH) <= 6){
                    quarter = 1;
                }
                else{
                    quarter = 2;
                }
            }
            courses = courseService.listByYearQuarter(year,quarter, 1, 10).getContent(); //TODO: falta agregar paginacion
            courses.sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());
            return Response.ok( new GenericEntity<List<CourseDto>>(courses.stream().map(CourseDto::fromCourse).collect(Collectors.toList())){}).build(); //TODO: este CourseDto estaria bueno que tenga el link al enroll de ese curso
        }
    }

    @GET
    @Path("/course/{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourse(@PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
            return Response.ok( CourseDto.fromCourse(course) ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/course/{courseId}/teachers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourseTeachers(@PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            List<User> courseTeachers = courseService.getTeachers(courseId);
            return Response.ok( new GenericEntity<List<UserDto>>(courseTeachers.stream().map(UserDto::fromUser).collect(Collectors.toList()) ){}).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/course/{courseId}/helpers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourseHelpers(@PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            List<User> courseHelpers = courseService.getHelpers(courseId);
            return Response.ok( new GenericEntity<List<UserDto>>(courseHelpers.stream().map(UserDto::fromUser).collect(Collectors.toList()) ){} ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/course/{courseId}/students")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourseStudents(@PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            List<User> enrolledStudents = userService.getStudentsByCourse(courseId, 1, 10).getContent(); //TODO: falta agregar paginacion
            return Response.ok( new GenericEntity<List<UserDto>>(enrolledStudents.stream().map(UserDto::fromUser).collect(Collectors.toList()) ){} ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



    @GET
    @Path("/course/{courseId}/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@PathParam("courseId") Long courseId) throws DtoValidationException{ //TODO: falta agregar paginacion

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (courseId != null){
            List<User> unenrolledUsers = courseService.listUnenrolledUsers(courseId);
            List<Role> roles = roleService.list();
            return Response.ok( EnrollUserToCourseResponseDto.fromUserToCourseInformation(unenrolledUsers, roles) ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/course/{courseId}/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@Valid UserToCourseFormDto userToCourseForm, @PathParam("courseId") Long courseId) throws DtoValidationException, URISyntaxException {

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (userToCourseForm != null){
            courseService.enroll(userToCourseForm.getUserId(), courseId, userToCourseForm.getRoleId());
            LOGGER.debug("User {} successfully enrolled in {}", userToCourseForm.getUserId(), courseId);
            URI courseUri = new URI(uriInfo.getBaseUri().normalize().toString() + "admin/course/" + courseId); //TODO: rev si termina quedando asi
            return Response.seeOther(courseUri).status(Response.Status.SEE_OTHER).build(); //TODO: veri si esta bien esta redireccion

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
