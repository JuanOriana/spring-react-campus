package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnnouncementsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);
    @Autowired
    AnnouncementService announcementService;

    @RequestMapping("/announcements")
    public ModelAndView announcements() {
        return new ModelAndView("announcements");
    }
}
