package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
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

        List<Subject> subjects = subjectService.list();

        if (subjects.isEmpty()){
            return Response.ok(Response.Status.NO_CONTENT).build();
        }

        return Response.ok( new GenericEntity<List<SubjectDto>>(subjects.stream().map(SubjectDto::fromSubject).collect(Collectors.toList())){}).build();
    }

    @POST
    @Path("/course/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newCourse(@Valid CourseFormDto courseForm) throws DtoValidationException, URISyntaxException { //TODO: startTimes y endTimes

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
    public Response getCourses(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize, @QueryParam("year") Integer year, @QueryParam("quarter") Integer quarter){

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CampusPage<Course> coursesPaginated;
        List<Integer> availableYears = courseService.getAvailableYears();

        if (year == null && quarter == null){
            List<Course> coursesList = courseService.list();

            if (coursesList.isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            coursesList.sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());
            return Response.ok( AllCoursesResponseDto.responseFrom(availableYears, coursesList)).build(); //TODO: paginacion?
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
            coursesPaginated = courseService.listByYearQuarter(year,quarter, page, pageSize);

            if (coursesPaginated.getContent().isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            coursesPaginated.getContent().sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());
            return Response.ok( new GenericEntity<List<CourseDto>>(coursesPaginated.getContent().stream().map(CourseDto::fromCourse).collect(Collectors.toList())){})
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", coursesPaginated.getPage() + 1).queryParam("pageSize", pageSize).queryParam("year", year).queryParam("quarter", quarter).build().toString(), "next")
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", Math.max(coursesPaginated.getPage() - 1, 1)).queryParam("pageSize", pageSize).queryParam("year", year).queryParam("quarter", quarter).build().toString(), "prev")
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).queryParam("pageSize", pageSize).queryParam("year", year).queryParam("quarter", quarter).build().toString(), "first")
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", coursesPaginated.getTotal()).queryParam("pageSize", pageSize).queryParam("year", year).queryParam("quarter", quarter).build().toString(), "last")
                    .build(); //TODO: este CourseDto estaria bueno que tenga el link al enroll de ese curso
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

            if (courseTeachers.isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

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

            if (courseHelpers.isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            return Response.ok( new GenericEntity<List<UserDto>>(courseHelpers.stream().map(UserDto::fromUser).collect(Collectors.toList()) ){} ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/course/{courseId}/students")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourseStudents(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize, @PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            CampusPage<User> enrolledStudents = userService.getStudentsByCourse(courseId, page, pageSize);

            if (enrolledStudents.getContent().isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            return Response.ok( new GenericEntity<List<UserDto>>(enrolledStudents.getContent().stream().map(UserDto::fromUser).collect(Collectors.toList()) ){} )
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", enrolledStudents.getPage() + 1).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "next") //TODO: condicionar esto a si es que tiene un next
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", Math.max(enrolledStudents.getPage() - 1, 1)).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "prev")
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "first")
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", enrolledStudents.getTotal()).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "last")
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



    @GET
    @Path("/course/{courseId}/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@PathParam("courseId") Long courseId) throws DtoValidationException{

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (courseId != null){
            List<User> unenrolledUsers = courseService.listUnenrolledUsers(courseId);

            if (unenrolledUsers.isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            List<Role> roles = roleService.list(); //TODO: hacer un endpoint separado?
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
            return Response.seeOther(courseUri).status(Response.Status.SEE_OTHER).build(); //TODO: ver si esta bien esta redireccion

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
