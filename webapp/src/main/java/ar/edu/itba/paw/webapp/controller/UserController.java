package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.form.UserProfileForm;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    public UserController(AuthFacade authFacade, UserService userService) {
        super(authFacade);
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView user(final UserProfileForm userProfileForm) {
        Optional<byte[]> optionalImage = userService.getProfileImage(authFacade.getCurrentUser().getUserId());
        ModelAndView mav = new ModelAndView("user");
        optionalImage.ifPresent(bytes -> mav.addObject("image", bytes));
        mav.addObject("userProfileForm",userProfileForm);
        return mav;
    }

    @PostMapping("/user")
    public ModelAndView user(@Valid UserProfileForm userProfileForm, final BindingResult errors) {
        if (!errors.hasErrors()){
            userService.updateProfileImage(authFacade.getCurrentUser().getUserId(),
                    userProfileForm.getImage().getBytes());
        }
        return user(userProfileForm);
    }

    @RequestMapping("/user/profile-image")
    public void profileImage(HttpServletResponse response) throws IOException {
        byte[] image = userService.getProfileImage(authFacade.getCurrentUser().getUserId())
               .orElseThrow(RuntimeException::new);
       InputStream is = new ByteArrayInputStream(image);
       IOUtils.copy(is,response.getOutputStream());
    }


}
