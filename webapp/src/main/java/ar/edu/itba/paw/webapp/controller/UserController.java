package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
public class UserController extends AuthController {

    private UserService userService;

    public UserController(AuthFacade authFacade) {
        super(authFacade);
    }

    @RequestMapping("/user")
    public ModelAndView user() {
        Optional<byte[]> optionalImage = userService.getProfileImage(authFacade.getCurrentUser().getUserId());
        ModelAndView mav = new ModelAndView("user");
        //optionalImage.ifPresent(bytes -> mav.addObject("image", bytes));
        return mav;
    }

    @RequestMapping("/user/profile-image")
    public void profileImage(HttpServletResponse response) throws IOException {
//        byte[] image = userService.getProfileImage(authFacade.getCurrentUser().getUserId())
//                .orElseThrow(RuntimeException::new);
//        InputStream is = new ByteArrayInputStream(image);
//        IOUtils.copy(is,response.getOutputStream());
    }


}
