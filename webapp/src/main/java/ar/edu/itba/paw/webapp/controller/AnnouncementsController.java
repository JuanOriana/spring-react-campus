package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import ar.edu.itba.paw.webapp.exception.PaginationException;
import ar.edu.itba.paw.webapp.implementation.AppPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.time.format.DateTimeFormatter;

@Controller
public class AnnouncementsController extends AuthController {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementsController(AuthFacade authFacade, AnnouncementService announcementService) {
        super(authFacade);
        this.announcementService = announcementService;
    }

    @GetMapping("/announcements")
    public ModelAndView announcements(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        ModelAndView mav = new ModelAndView("announcements");
        CampusUser currentUser = authFacade.getCurrentUser();
        if(!announcementService.isPaginationValid(currentUser.getUserId(), page, pageSize)) throw new PaginationException();
        Page<Announcement> announcements = announcementService.findAnnouncementByPage(currentUser.getUserId(),
                AppPageRequest.of(page, pageSize, Sort.by("date").descending()));
        mav.addObject("announcementList", announcements.getContent());
        mav.addObject("currentPage", announcements.getPageable().getPageNumber());
        mav.addObject("maxPage", announcements.getTotalPages());
        mav.addObject("pageSize", announcements.getPageable().getPageSize());
        mav.addObject("dateTimeFormatter", dateTimeFormatter);
        return mav;
    }

    @DeleteMapping(value = "/announcements/{announcementId}")
    @ResponseBody
    public void deleteAnnouncement(@PathVariable Long announcementId) {
        announcementService.delete(announcementId);
    }


}
