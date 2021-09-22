package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.CourseForm;
import ar.edu.itba.paw.webapp.form.UserRegisterForm;
import ar.edu.itba.paw.webapp.form.UserToCourseForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AuthController{
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final UserService userService;

    private final SubjectService subjectService;

    private final CourseService courseService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, SubjectService subjectService, CourseService courseService, RoleService roleService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.courseService = courseService;
        this.roleService = roleService;
    }


    @RequestMapping(value = "/portal")
    public ModelAndView adminPortal(final String successMessage){
        ModelAndView mav = new  ModelAndView("admin/admin-portal");
        mav.addObject("successMessage",successMessage);
        return mav;
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public ModelAndView newUser(final UserRegisterForm userRegisterForm){
        ModelAndView mav = new ModelAndView("admin/new-user");
        mav.addObject("userRegisterForm",userRegisterForm);
        return mav;
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
    public ModelAndView newUser(@Valid UserRegisterForm userRegisterForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            userService.create(userRegisterForm.getFileNumber(),userRegisterForm.getName(),userRegisterForm.getSurname(),
                    userRegisterForm.getUsername(),userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(),false);
            return adminPortal("Usuario creado exitosamente");
        }
        return newUser(userRegisterForm);
    }

    @RequestMapping(value = "/course/new", method = RequestMethod.GET)
    public ModelAndView newCourse(final CourseForm courseForm){
        ModelAndView mav = new ModelAndView("admin/new-course");
        mav.addObject("courseForm",courseForm);
        mav.addObject("subjects", subjectService.list());
        mav.addObject("users",userService.list());
        return mav;
    }

    @RequestMapping(value = "/course/new", method = RequestMethod.POST)
    public ModelAndView newCourse(@Valid CourseForm courseForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            courseService.create(courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard()
                    , courseForm.getSubjectId());
            return adminPortal("Curso creado exitosamente");
        }
        return newCourse(courseForm);
    }

    @RequestMapping(value = "/course/select", method = RequestMethod.GET)
    public ModelAndView selectCourse(){
        ModelAndView mav = new ModelAndView("admin/select-course");
        mav.addObject("courses",courseService.list());
        return mav;
    }

    @RequestMapping(value = "/course/enroll", method = RequestMethod.GET)
    public ModelAndView addUserToCourse(final UserToCourseForm userToCourseForm,
                                        @RequestParam(name = "courseId") Long courseId,
                                        final String successMessage){
        ModelAndView mav = new ModelAndView("admin/add-user-to-course");
        List<User> courseStudents = courseService.getStudents(courseId);
        Set<User> courseTeachers = courseService.getTeachers(courseId).keySet();
        List<User> remainingUsers = userService.list();
        remainingUsers.removeAll(courseStudents);
        remainingUsers.removeAll(courseTeachers);
        mav.addObject("userToCourseForm",userToCourseForm);
        mav.addObject("users",remainingUsers);
        mav.addObject("course",courseService.getById(courseId).orElseThrow(RuntimeException::new));
        mav.addObject("roles",roleService.list());
        mav.addObject("courseStudents", courseStudents);
        mav.addObject("courseTeachers", courseTeachers);
        mav.addObject("successMessage",successMessage);
        return mav;
    }

    @RequestMapping(value = "/course/enroll", method = RequestMethod.POST)
    public ModelAndView addUserToCourse(@Valid UserToCourseForm userToCourseForm, final BindingResult errors,
                                        @RequestParam(name = "courseId") Long courseId){
        String successMessage = "";
        if (!errors.hasErrors()) {
            courseService.enroll(userToCourseForm.getUserId(),courseId,userToCourseForm.getRoleId());
            successMessage ="Usuario agregado exitosamente";
        }
        return addUserToCourse(userToCourseForm,courseId,successMessage);
    }
}
