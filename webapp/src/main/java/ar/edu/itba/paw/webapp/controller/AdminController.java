package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.form.UserRegisterForm;
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

    @RequestMapping(value = "/portal")
    public ModelAndView adminPortal(){
        return new ModelAndView("admin/admin-portal");
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public ModelAndView newUser(final UserRegisterForm userRegisterForm, final String successMessage){
        ModelAndView mav = new ModelAndView("admin/new-user");
        mav.addObject("userRegisterForm",userRegisterForm);
        mav.addObject("successMessage",successMessage);
        return mav;
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public ModelAndView newUser(@Valid UserRegisterForm userRegisterForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            userService.create(userRegisterForm.getFileNumber(),userRegisterForm.getName(),userRegisterForm.getSurname(),
                    userRegisterForm.getUsername(),userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(),false);
            return adminPortal();
        }
        return newUser(userRegisterForm,"Usuario registrado correctamente");
    }

    @RequestMapping(value = "/newcourse")
    public ModelAndView newCourse(){
        return new ModelAndView("admin/new-course");
    }
}
