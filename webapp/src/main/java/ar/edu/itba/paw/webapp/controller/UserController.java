package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.form.UserProfileForm;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController extends AuthController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(AuthFacade authFacade, UserService userService) {
        super(authFacade);
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ModelAndView user(final UserProfileForm userProfileForm) {
        ModelAndView mav = new ModelAndView("user");
        mav.addObject("userProfileForm", userProfileForm);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ModelAndView user(@Valid UserProfileForm userProfileForm, final BindingResult errors) {
        if (!errors.hasErrors()){
            userService.updateProfileImage(authFacade.getCurrentUser().getUserId(),
                    userProfileForm.getImage().getBytes());
            return new ModelAndView("redirect:/user");
        }
        LOGGER.debug("Could not update image");
        return user(userProfileForm);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/profile-image/{userId}")
    public void profileImage(@PathVariable(value = "userId") Long userId,
                             HttpServletResponse response) throws IOException {
        Optional<byte[]> image = userService.getProfileImage(userId);
        response.setContentType("image/*");
        response.setHeader("Content-Disposition", "filename=\"" + "profile-img-" + userId +"\"");
        InputStream is = null;
        if (!image.isPresent()) {
            File file = ResourceUtils.getFile("classpath:default-user-image.png");
            InputStream fileStream = new FileInputStream(file);
            byte[] data = IOUtils.toByteArray(Objects.requireNonNull(fileStream));
            is = new ByteArrayInputStream(data);
            LOGGER.debug("User {} does not have an image", userId);
        } else {
            is = new ByteArrayInputStream(image.get());
        }


        IOUtils.copy(is,response.getOutputStream());
    }


}
