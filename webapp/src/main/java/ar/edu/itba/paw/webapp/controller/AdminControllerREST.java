package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.CourseFormDto;
import ar.edu.itba.paw.webapp.dto.NextFileNumberDto;
import ar.edu.itba.paw.webapp.dto.UserRegisterFormDto;
import ar.edu.itba.paw.webapp.dto.UserToCourseFormDto;
import ar.edu.itba.paw.webapp.form.UserToCourseForm;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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

    @GET
    @Path("/user/new")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getNextFileNumber(){
        return Response.ok(NextFileNumberDto.fromNextFileNumber(userService.getMaxFileNumber() + 1)).build();
    }

    @POST
    @Path("/user/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newUser(@Valid UserRegisterFormDto userRegisterForm) throws DtoValidationException {

        if (userRegisterForm != null) {
            DtoValidator.validate(userRegisterForm, "Failed to validate new user attributes");
            User user = userService.create(userRegisterForm.getFileNumber(), userRegisterForm.getName(), userRegisterForm.getSurname(),
                    userRegisterForm.getUsername(), userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(), false);
            LOGGER.debug("User of name {} created", user.getUsername());
            return Response.ok(new GenericEntity<User>(user) {
            }).status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/course/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newCourse(@Valid CourseFormDto courseForm) throws DtoValidationException, URISyntaxException { //TODO: ver bien como seria la parte del json para startTimes y endTimes
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

    @POST
    @Path("/course/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@Valid UserToCourseFormDto userToCourseForm, @QueryParam("courseId") Long courseId) throws DtoValidationException{
        if (userToCourseForm != null){
            courseService.enroll(userToCourseForm.getUserId(), courseId, userToCourseForm.getRoleId());
            LOGGER.debug("User {} successfully enrolled in {}", userToCourseForm.getUserId(), courseId);
            return Response.ok().status(Response.Status.ACCEPTED).build(); //TODO: devolver algo mas?
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

//    @GET
//    @Path("/course/all")
//    @Produces(value = {MediaType.APPLICATION_JSON, })
//    public Response allCourses(@QueryParam("year") Integer year, @QueryParam("quarter") Integer quarter) { //TODO: falta agregar paginacion
//        if (year == null){
//            year = Calendar.getInstance().get(Calendar.YEAR);
//        }
//        if (quarter == null){
//            if (Calendar.getInstance().get(Calendar.MONTH) <= 6){
//                quarter = 1;
//            }
//            else{
//                quarter = 2;
//            }
//        }
//
////        CampusPage<Course> courses = courseService.listByYearQuarter(year,quarter, page, pageSize);
////        mav.addObject("courses", courses.getContent());
////        mav.addObject("year",year);
////        mav.addObject("allYears",courseService.getAvailableYears());
////        mav.addObject("quarter",quarter);
////        mav.addObject("currentPage", courses.getPage());
////        mav.addObject("maxPage", courses.getTotal());
////        mav.addObject("pageSize", courses.getSize());
////        return mav;
//    }

}
