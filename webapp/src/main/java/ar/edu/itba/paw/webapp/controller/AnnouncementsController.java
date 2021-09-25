package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Controller
public class AnnouncementsController extends AuthController {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final Comparator<Announcement> orderByDate = (o1,o2) -> o2.getDate().compareTo(o1.getDate());
    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementsController(AuthFacade authFacade, AnnouncementService announcementService) {
        super(authFacade);
        this.announcementService = announcementService;
    }

    @RequestMapping("/announcements")
    public ModelAndView announcements(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        ModelAndView mav = new ModelAndView("announcements");
        CampusUser currentUser = authFacade.getCurrentUser();
        List<Announcement> announcements = announcementService.list(currentUser.getUserId(), page, pageSize, orderByDate);
        mav.addObject("announcementList", announcements);
        mav.addObject("currentPage",page);
        mav.addObject("maxPage", announcementService.getPageCount(currentUser.getUserId(), pageSize));
        mav.addObject("pageSize",pageSize);
        mav.addObject("dateTimeFormatter",dateTimeFormatter);
        return mav;
    }

    @DeleteMapping(value = "/announcements/{announcementId}")
    @ResponseBody
    public void deleteAnnouncement(@PathVariable Long announcementId) {
        announcementService.delete(announcementId);
    }


}
