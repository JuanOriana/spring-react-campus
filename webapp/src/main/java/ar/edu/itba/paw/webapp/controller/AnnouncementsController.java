package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@Controller
public class AnnouncementsController extends AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);
    private final Comparator<Announcement> orderByDate = (o1,o2) -> o2.getDate().compareTo(o1.getDate());

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    AuthFacade authFacade;

    @RequestMapping("/announcements")
    public ModelAndView announcements(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        ModelAndView mav = new ModelAndView("announcements");
        CampusUser currentUser = authFacade.getCurrentUser();
        int pageCount = announcementService.getPageCount(currentUser.getUserId(), pageSize);
        if (page < 1) page = 1;
        else if (page > pageCount) page = pageCount;
        if(pageSize < 1) pageSize = 10;
        else if(pageSize > 50) pageSize = 50;
        List<Announcement> announcements = announcementService.list(currentUser.getUserId(), page, pageSize, orderByDate);
        mav.addObject("announcementList", announcements);
        mav.addObject("currentPage",page);
        mav.addObject("maxPage", pageCount);
        mav.addObject("pageSize",pageSize);
        return mav;
    }

    @RequestMapping(value = "/announcements/{announcementId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAnnouncement(@PathVariable Long announcementId) {
        announcementService.delete(announcementId);
    }


}
