package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.time.format.DateTimeFormatter;

@Controller
public class AnnouncementsController extends AuthController {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final AnnouncementService announcementService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);

    @Autowired
    public AnnouncementsController(AuthFacade authFacade, AnnouncementService announcementService) {
        super(authFacade);
        this.announcementService = announcementService;
    }

    @GetMapping("/announcements")
    public ModelAndView announcements(@RequestParam(value = "page", required = false, defaultValue = "1")
                                              Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                              Integer pageSize) {
        ModelAndView mav = new ModelAndView("announcements");
        CampusPage<Announcement> announcements = announcementService.listByUser(authFacade.getCurrentUserId(),
                page, pageSize);
        mav.addObject("announcementList", announcements.getContent());
        mav.addObject("currentPage", announcements.getPage());
        mav.addObject("maxPage", announcements.getTotal());
        mav.addObject("pageSize", announcements.getSize());
        mav.addObject("dateTimeFormatter", dateTimeFormatter);
        return mav;
    }

    @DeleteMapping(value = "/announcements/{announcementId}")
    @ResponseBody
    public void deleteAnnouncement(@PathVariable Long announcementId) {
        LOGGER.debug("Deleting announcement {}", announcementId);
        announcementService.delete(announcementId);
    }


}
