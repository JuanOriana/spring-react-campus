package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@Controller
public class AnnouncementsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);
    private final Comparator<Announcement> orderByDate = (o1,o2) -> o2.getDate().compareTo(o1.getDate());

    @Autowired
    AnnouncementService announcementService;

    @RequestMapping("/announcements")
    public ModelAndView announcements(@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Long pageSize) {
        ModelAndView mav = new ModelAndView("announcements");
        int pageCount = announcementService.getPageCount(pageSize);
        if (page < 1) page = 1L;
        else if (page > pageCount) page = (long) pageCount;
        if(pageSize < 1) pageSize = 10L;
        else if(pageSize > 50) pageSize = 50L;
        List<Announcement> announcements = announcementService.list(page, pageSize, orderByDate);
        mav.addObject("announcementList", announcements);
        mav.addObject("currentPage",page);
        mav.addObject("maxPage", pageCount);
        mav.addObject("pageSize",pageSize);
        return mav;
    }


}
