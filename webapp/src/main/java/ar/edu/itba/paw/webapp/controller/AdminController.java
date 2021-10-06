package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.form.CourseForm;
import ar.edu.itba.paw.webapp.form.UserRegisterForm;
import ar.edu.itba.paw.webapp.form.UserToCourseForm;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AuthController {
    private final UserService userService;
    private final SubjectService subjectService;
    private final CourseService courseService;
    private final RoleService roleService;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    @Autowired
    public AdminController(AuthFacade authFacade, UserService userService, SubjectService subjectService,
                           CourseService courseService, RoleService roleService) {
        super(authFacade);
        this.userService = userService;
        this.subjectService = subjectService;
        this.courseService = courseService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/portal")
    public ModelAndView adminPortal(){
        return new  ModelAndView("admin/admin-portal");
    }

    @GetMapping(value = "/user/new")
    public ModelAndView newUser(final UserRegisterForm userRegisterForm){
        ModelAndView mav = new ModelAndView("admin/new-user");
        mav.addObject("userRegisterForm", userRegisterForm);
        return mav;
    }

    @PostMapping(value = "/user/new")
    public ModelAndView newUser(@Valid UserRegisterForm userRegisterForm, final BindingResult validation,
                                RedirectAttributes redirectAttributes) {
        if (!validation.hasErrors()) {
           User user =userService.create(userRegisterForm.getFileNumber(), userRegisterForm.getName(), userRegisterForm.getSurname(),
                    userRegisterForm.getUsername(), userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(), false);
           LOGGER.debug("User of name {} created", user.getUsername());
           redirectAttributes.addFlashAttribute("successMessage", "admin.success.message");
           return new ModelAndView("redirect:/admin/portal");
        }
        return newUser(userRegisterForm);
    }

    @GetMapping(value = "/course/new")
    public ModelAndView newCourse(final CourseForm courseForm){
        ModelAndView mav = new ModelAndView("admin/new-course");
        List<Subject> subjects = subjectService.list();
        subjects.sort(Comparator.comparing(Subject::getName));
        mav.addObject("courseForm",courseForm);
        mav.addObject("subjects", subjects);
        mav.addObject("days",days);
        return mav;
    }

    @PostMapping(value = "/course/new")
    public ModelAndView newCourse(@Valid CourseForm courseForm, final BindingResult validation) {
        if(!validation.hasErrors()) {
            Course course = courseService.create(courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard()
                    , courseForm.getSubjectId(), courseForm.getStartTimes(), courseForm.getEndTimes());
            LOGGER.debug("Created course in year {} in quarter {} of subjectId {} with id {}", courseForm.getYear(),
                    courseForm.getBoard(), courseForm.getSubjectId(), course.getCourseId());
            return new ModelAndView("redirect:/admin/course/enroll?courseId="+course.getCourseId());
        }
        return newCourse(courseForm);
    }

    @GetMapping(value = "/course/select")
    public ModelAndView selectCourse(){
        ModelAndView mav = new ModelAndView("admin/select-course");
        List<Course> courses = courseService.list();
        courses.sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());
        mav.addObject("courses",courses);
        return mav;
    }

    @GetMapping(value = "/course/enroll")
    public ModelAndView addUserToCourse(final UserToCourseForm userToCourseForm,
                                        @RequestParam(name = "courseId") Long courseId,
                                        final String successMessage){
        ModelAndView mav = new ModelAndView("admin/add-user-to-course");
        List<User> unenrolledUsers = courseService.listUnenrolledUsers(courseId);
        unenrolledUsers.sort(Comparator.comparingInt(User::getFileNumber));
        mav.addObject("userToCourseForm",userToCourseForm);
        mav.addObject("users", unenrolledUsers);
        mav.addObject("course",courseService.findById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("roles",roleService.list());
        mav.addObject("courseStudents", courseService.getStudents(courseId));
        mav.addObject("courseTeachers", courseService.getTeachers(courseId).keySet());
        mav.addObject("successMessage",successMessage);
        return mav;
    }

    @PostMapping(value = "/course/enroll")
    public ModelAndView addUserToCourse(@Valid UserToCourseForm userToCourseForm, final BindingResult errors,
                                        @RequestParam(name = "courseId") Long courseId){
        String successMessage = "";
        if (!errors.hasErrors()) {
            courseService.enroll(userToCourseForm.getUserId(), courseId, userToCourseForm.getRoleId());
            successMessage ="user.success.message";
            LOGGER.debug("User {} succesfully enrolled in {}", userToCourseForm.getUserId(), courseId);
        }
        return addUserToCourse(userToCourseForm,courseId,successMessage);
    }

    @GetMapping(value = "/course/all")
    public ModelAndView allCourses(@RequestParam(value = "year", defaultValue = "") Integer year,
                                   @RequestParam(value = "quarter", defaultValue = "") Integer quarter) {
        ModelAndView mav = new ModelAndView("admin/all-courses");
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
        mav.addObject("courses", courseService.listByYearQuarter(year,quarter));
        mav.addObject("year",year);
        mav.addObject("allYears",courseService.getAvailableYears());
        mav.addObject("quarter",quarter);
        return mav;
    }
}
