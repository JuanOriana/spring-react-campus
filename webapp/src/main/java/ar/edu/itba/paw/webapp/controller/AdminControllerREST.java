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
@ComponentScan({"ar.edu.itba.paw.webapp.constraint.validator"}) //TODO: ver si esto se puede sacar, se lo puse por que sino no me encontraba el bean DtoConstraintValidator
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

    //TODO: los metodos de admin de users/courses, podrian ir en el UserController/CourseController directamente, bajo algun path de admins (idea)

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
    @Path("/course/new")
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
            URI enrollUsers = new URI(uriInfo.getBaseUri().normalize().toString() + "admin/course/enroll?courseId=" + course.getCourseId());
            return Response.seeOther(enrollUsers).status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/course/select")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response selectCourse(){

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<Course> courses = courseService.list();
        courses.sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());
        return Response.ok( new GenericEntity<List<CourseDto>>(courses.stream().map(CourseDto::fromCourse).collect(Collectors.toList())){}).build(); //TODO: este CourseDto estaria bueno que tenga el link al enroll de ese curso
    }

    @GET
    @Path("/course/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@QueryParam("courseId") Long courseId) throws DtoValidationException{ //TODO: falta agregar paginacion

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (courseId != null){
            Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
            List<User> unenrolledUsers = courseService.listUnenrolledUsers(courseId);
            CampusPage<User> enrolledStudents = userService.getStudentsByCourse(courseId, 1, 10); //TODO: falta agregar paginacion
            List<User> courseTeachers = courseService.getTeachers(courseId);
            List<User> courseHelpers = courseService.getHelpers(courseId);
            List<Role> roles = roleService.list();
            return Response.ok( EnrollUserToCourseResponseDto.fromUserToCourseInformation(course, unenrolledUsers, enrolledStudents.getContent(), courseTeachers, courseHelpers, roles) ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/course/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@Valid UserToCourseFormDto userToCourseForm, @QueryParam("courseId") Long courseId) throws DtoValidationException{

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (userToCourseForm != null){
            courseService.enroll(userToCourseForm.getUserId(), courseId, userToCourseForm.getRoleId());
            LOGGER.debug("User {} successfully enrolled in {}", userToCourseForm.getUserId(), courseId);
            return Response.ok().status(Response.Status.ACCEPTED).build(); //TODO: devolver algo mas?
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/course/all")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response allCourses(@QueryParam("year") Integer year, @QueryParam("quarter") Integer quarter) { //TODO: falta agregar paginacion

        if (!isAdminUser()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

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
        List<Integer> availableYears = courseService.getAvailableYears();
        CampusPage<Course> courses = courseService.listByYearQuarter(year,quarter, 1, 10); //TODO: falta agregar paginacion
        return Response.ok( AllCoursesResponseDto.responseFrom(availableYears, courses.getContent())).build();
    }

}
