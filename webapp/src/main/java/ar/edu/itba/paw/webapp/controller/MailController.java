package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MailingService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
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

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MailController extends AuthController{

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalController.class);

    private final UserService userService;
    private final MailingService mailingService;
    private final AuthFacade authFacade;

    @Autowired
    public MailController(UserService userService, MailingService mailingService, AuthFacade authFacade) {
        this.userService = userService;
        this.mailingService = mailingService;
        this.authFacade = authFacade;
    }

    @RequestMapping(value = "/sendmail/{userId}", method = RequestMethod.GET)
    public ModelAndView sendmail(@PathVariable Long userId, final MailForm mailForm,
                                 String successMessage) {
        User user = userService.findById(userId).orElseThrow(RuntimeException::new);
        ModelAndView mav = new ModelAndView("sendmail");
        mav.addObject("user", user);
        mav.addObject("mailForm",mailForm);
        mav.addObject("successMessage",successMessage);
        return mav;
    }

    @RequestMapping(value = "/sendmail/{userId}", method = RequestMethod.POST)
    public ModelAndView sendmail(@PathVariable Long userId,
                                         @Valid MailForm mailForm, final BindingResult errors){
        String successMessage = null;
        if (!errors.hasErrors()) {
            Optional<User> user = userService.findById(userId);
            if (user.isPresent()) {
                mailingService.sendEmail(authFacade.getCurrentUser().getEmail(), user.get().getEmail(), mailForm.getSubject(), mailForm.getContent(), "text/plain"); // todo desharcodear el content type
            }
            else{
                // todo: Manejo
            }
            mailForm.setSubject("");
            mailForm.setContent("");
            successMessage = "Email enviado exitosamente";
        }
        return sendmail(userId, mailForm, successMessage);
    }

}