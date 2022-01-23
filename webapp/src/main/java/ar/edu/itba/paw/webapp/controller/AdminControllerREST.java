package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Path("admin")
@Component
@ComponentScan({"ar.edu.itba.paw.webapp.constraint.validator"})
public class AdminControllerREST {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private DtoConstraintValidator dtoValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private ResponsePaging<Course> courseResponsePaging;

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
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(NextFileNumberDto.fromNextFileNumber(userService.getMaxFileNumber() + 1)).build();
    }

    @POST
    @Path("/user/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newUser(@Valid UserRegisterFormDto userRegisterForm) throws DtoValidationException {

        if (!isAdminUser()){
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
    @Path("/subjects")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getSubjects(){

        if (!isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<Subject> subjects = subjectService.list();

        if (subjects.isEmpty()){
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }

        return Response.ok( new GenericEntity<List<SubjectDto>>(subjects.stream().map(SubjectDto::fromSubject).collect(Collectors.toList())){}).build();
    }

    @POST
    @Path("/course/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newCourse(@Valid CourseFormDto courseForm) throws DtoValidationException, URISyntaxException { //TODO: startTimes y endTimes

        if (!isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(courseForm != null) {
            Course course = courseService.create(courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard()
                    , courseForm.getSubjectId(), courseForm.getStartTimes(), courseForm.getEndTimes());
            LOGGER.debug("Created course in year {} in quarter {} of subjectId {} with id {}", courseForm.getYear(),
                    courseForm.getBoard(), courseForm.getSubjectId(), course.getCourseId());
            URI enrollUsers = new URI(uriInfo.getBaseUri().normalize() + "admin/course/" + course.getCourseId() + "/enroll"); //TODO: rev si termina quedando asi
            return Response.seeOther(enrollUsers).status(Response.Status.SEE_OTHER).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/course/{courseId}/edit")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response editCourse(@PathParam("courseId") Long courseId) throws DtoValidationException {

        if (!isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (courseId != null){
            return Response.ok(CourseFormDto.fromCourse(courseService.findById(courseId).orElseThrow(CourseNotFoundException::new), timetableService.getStartTimesOf(courseId), timetableService.getEndTimesOf(courseId))).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/course/{courseId}/edit")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response editCourse(@PathParam("courseId") Long courseId, @Valid CourseFormDto courseForm) throws DtoValidationException{ //TODO: startTimes y endTimes
        if (!isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(courseForm != null) {
            if (courseService.update(courseId, courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard(), courseForm.getSubjectId(), courseForm.getStartTimes(), courseForm.getEndTimes())){
                LOGGER.debug("Updated course in year {} in quarter {} of subjectId {} with id {}", courseForm.getYear(),
                        courseForm.getBoard(), courseForm.getSubjectId(), courseId);
                return Response.ok(Response.Status.ACCEPTED).build(); //TODO: ver si retornar algo mas
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/courses")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourses(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize, @QueryParam("year") Integer year, @QueryParam("quarter") Integer quarter){

        if (!isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        CampusPage<Course> coursesPaginated;
        List<Integer> availableYears = courseService.getAvailableYears();

        if (year == null && quarter == null){
            List<Course> coursesList = courseService.list();

            if (coursesList.isEmpty()){
                return Response.ok().status(Response.Status.NO_CONTENT).build();
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
                return Response.ok().status(Response.Status.NO_CONTENT).build();
            }

            coursesPaginated.getContent().sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());

            Response.ResponseBuilder response = Response.ok( new GenericEntity<List<CourseDto>>(coursesPaginated.getContent().stream().map(CourseDto::fromCourse).collect(Collectors.toList())){});

            courseResponsePaging.paging(coursesPaginated, response, uriInfo, pageSize);

            return response.build(); //TODO: este CourseDto estaria bueno que tenga el link al enroll de ese curso
        }
    }


    @GET
    @Path("/course/{courseId}/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@PathParam("courseId") Long courseId) throws DtoValidationException{

        if (!isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (courseId != null){
            List<User> unenrolledUsers = courseService.listUnenrolledUsers(courseId);

            if (unenrolledUsers.isEmpty()){
                return Response.ok().status(Response.Status.NO_CONTENT).build();
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
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (userToCourseForm != null){
            courseService.enroll(userToCourseForm.getUserId(), courseId, userToCourseForm.getRoleId());
            LOGGER.debug("User {} successfully enrolled in {}", userToCourseForm.getUserId(), courseId);
            URI courseUri = new URI(uriInfo.getBaseUri().normalize()+ "admin/course/" + courseId); //TODO: rev si termina quedando asi
            return Response.seeOther(courseUri).status(Response.Status.SEE_OTHER).build(); //TODO: ver si esta bien esta redireccion

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
