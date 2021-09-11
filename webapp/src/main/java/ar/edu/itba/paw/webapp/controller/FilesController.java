package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);
    @RequestMapping("/files")
    public ModelAndView files(){
        return new ModelAndView("files");
    }
}
