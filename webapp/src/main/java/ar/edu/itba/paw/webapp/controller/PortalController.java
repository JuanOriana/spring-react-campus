package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class PortalController extends AuthController{

    private final CourseService courseService;
    protected final RoleService roleService;
    protected final SubjectService subjectService;

    @Autowired
    public PortalController(AuthFacade authFacade,
                            CourseService courseService, RoleService roleService, SubjectService subjectService) {
        super(authFacade);
        this.courseService = courseService;
        this.roleService = roleService;
        this.subjectService = subjectService;
    }

    @RequestMapping("/")
    public String rootRedirect() {
        if(authFacade.getCurrentUser().isAdmin()) {
            return "redirect:/admin/portal";
        }
        return "redirect:/portal";
    }

    @RequestMapping("/portal")
    public ModelAndView portal() {
        ModelAndView mav = new ModelAndView("portal");
        Long userId = authFacade.getCurrentUser().getUserId();
        List<Course> courses = courseService.list(userId);
        List<Course> coursesAsStudent = courseService.getCoursesWhereStudent(userId);
        System.out.println(coursesAsStudent);
        mav.addObject("courseList", courses);
        mav.addObject("coursesAsStudent", coursesAsStudent);
        return mav;
    }

}
