package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MailingService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.MailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MailController extends AuthController{

    private final UserService userService;
    private final MailingService mailingService;

    @Autowired
    public MailController(AuthFacade authFacade,
                          UserService userService, MailingService mailingService) {
        super(authFacade);
        this.userService = userService;
        this.mailingService = mailingService;
    }

    @GetMapping(value = "/mail/{userId}")
    public ModelAndView sendmail(@PathVariable Long userId, final MailForm mailForm,
                                 String successMessage) {
        ModelAndView mav = new ModelAndView("sendmail");
        mav.addObject("user", userService.findById(userId).orElseThrow(RuntimeException::new));
        mav.addObject("mailForm",mailForm);
        mav.addObject("successMessage",successMessage);
        return mav;
    }

    @PostMapping(value = "/mail/{userId}")
    public ModelAndView sendmail(@PathVariable Long userId,
                                         @Valid MailForm mailForm, final BindingResult errors){
        String successMessage = null;
        if (!errors.hasErrors()) {
            User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
            mailingService.sendTextPlainEmail(authFacade.getCurrentUser().getEmail(), user.getEmail(),
                        mailForm.getSubject(), mailForm.getContent());
            mailForm.setSubject("");
            mailForm.setContent("");
            successMessage = "email.success.message";
        }
        //TODO: REDIRECCIONAR A PROFESRES
        return sendmail(userId, mailForm, successMessage);
    }

}