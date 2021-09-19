package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.form.CourseForm;
import ar.edu.itba.paw.webapp.form.UserRegisterForm;
import ar.edu.itba.paw.webapp.form.UserToCourseForm;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AuthController{
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    CourseService courseService;

    @Autowired
    RoleService roleService;


    @RequestMapping(value = "/portal")
    public ModelAndView adminPortal(final String successMessage){
        ModelAndView mav = new  ModelAndView("admin/admin-portal");
        mav.addObject("successMessage",successMessage);
        return mav;
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public ModelAndView newUser(final UserRegisterForm userRegisterForm){
        ModelAndView mav = new ModelAndView("admin/new-user");
        mav.addObject("userRegisterForm",userRegisterForm);
        return mav;
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public ModelAndView newUser(@Valid UserRegisterForm userRegisterForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            userService.create(userRegisterForm.getFileNumber(),userRegisterForm.getName(),userRegisterForm.getSurname(),
                    userRegisterForm.getUsername(),userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(),false);
            return adminPortal("Usuario creado exitosamente");
        }
        return newUser(userRegisterForm);
    }

    @RequestMapping(value = "/newcourse", method = RequestMethod.GET)
    public ModelAndView newCourse(final CourseForm courseForm){
        ModelAndView mav = new ModelAndView("admin/new-course");
        mav.addObject("courseForm",courseForm);
        mav.addObject("subjects", subjectService.list());
        mav.addObject("users",userService.list());
        return mav;
    }

    @RequestMapping(value = "/newcourse", method = RequestMethod.POST)
    public ModelAndView newCourse(@Valid CourseForm courseForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            //TODO: MAKE IT FUNCTIONAL WHEN CREATE IS UPDATED
//            courseService.create(courseForm.getYear(),courseForm.getQuarter(),
//                    courseForm.getBoard(),courseForm.getSubjectId());
            return adminPortal("Curso creado exitosamente");
        }
        return newCourse(courseForm);
    }

    @RequestMapping(value = "/addusertocourse", method = RequestMethod.GET)
    public ModelAndView addUserToCourse(final UserToCourseForm userToCourseForm){
        ModelAndView mav = new ModelAndView("admin/add-user-to-course");
        mav.addObject("userToCourseForm",userToCourseForm);
        mav.addObject("users",userService.list());
        mav.addObject("courses",courseService.list());
        mav.addObject("roles",roleService.list());
        return mav;
    }

    @RequestMapping(value = "/addusertocourse", method = RequestMethod.POST)
    public ModelAndView addUserToCourse(@Valid UserToCourseForm userToCourseForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            courseService.enroll(userToCourseForm.getUserId(),userToCourseForm.getCourseId(),userToCourseForm.getRoleId());
            return adminPortal("Usuario agregado exitosamente");
        }
        return addUserToCourse(userToCourseForm);
    }
}
