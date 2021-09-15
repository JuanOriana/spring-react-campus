package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.MailingService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import ar.edu.itba.paw.webapp.form.AnnouncementForm;
import ar.edu.itba.paw.webapp.form.MailForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    UserService userService;
    @Autowired
    MailingService mailingService;
    @Autowired
    AuthFacade authFacade;

    @RequestMapping(value = "/sendmail/{userId}", method = RequestMethod.GET)
    public ModelAndView sendmail(@PathVariable Integer userId, final MailForm mailForm) {
        User user = userService.findById(userId).orElseThrow(RuntimeException::new);
        ModelAndView mav = new ModelAndView("sendmail");
        mav.addObject("user", user);
        mav.addObject("mailForm",mailForm);
        return mav;
    }

    @RequestMapping(value = "/sendmail/{userId}", method = RequestMethod.POST)
    public ModelAndView sendmail(@PathVariable Integer userId,
                                         @Valid MailForm mailForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            User user = userService.findById(userId).get();
            mailingService.sendEmail(authFacade.getCurrentUser().getEmail(),user.getEmail(),mailForm.getSubject(), mailForm.getContent(), "text/plain"); // todo desharcodear el content type
            mailForm.setSubject("");
            mailForm.setContent("");
        }


        return sendmail(userId, mailForm);
    }

}