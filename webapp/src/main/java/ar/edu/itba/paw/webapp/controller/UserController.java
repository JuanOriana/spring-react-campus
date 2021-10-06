package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.form.UserProfileForm;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
public class UserController extends AuthController {

    private final UserService userService;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(AuthFacade authFacade, UserService userService) {
        super(authFacade);
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView user(final UserProfileForm userProfileForm) {
        ModelAndView mav = new ModelAndView("user");
        mav.addObject("userProfileForm",userProfileForm);
        return mav;
    }

    @PostMapping("/user")
    public ModelAndView user(@Valid UserProfileForm userProfileForm, final BindingResult errors) {
        if (!errors.hasErrors()){
            userService.updateProfileImage(authFacade.getCurrentUser().getUserId(),
                    userProfileForm.getImage().getBytes());
        }
        LOGGER.debug("Could not update image");
        return user(userProfileForm);
    }

    @GetMapping("/user/profile-image/{userId}")
    public void profileImage(@PathVariable(value = "userId") Long userId,
                             HttpServletResponse response) throws IOException {
        Optional<byte[]> image = userService.getProfileImage(userId);
        if (!image.isPresent()) {
            LOGGER.debug("User " + userId + "does not have an image");
            return;
        }
        response.setContentType("image/*");
        response.setHeader("Content-Disposition", "filename=\"" + "profile-img-" + userId +"\"");
        InputStream is = new ByteArrayInputStream(image.get());
        IOUtils.copy(is,response.getOutputStream());
    }


}
