package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import ar.edu.itba.paw.webapp.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.exception.FileNotFoundException;
import ar.edu.itba.paw.webapp.form.AnnouncementForm;
import ar.edu.itba.paw.webapp.form.FileForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping(value = "/course")
public class CourseController extends AuthController {
    private final AuthFacade authFacade;
    private final AnnouncementService announcementService;
    private final CourseService courseService;
    private final FileCategoryService fileCategoryService;
    private final FileExtensionService fileExtensionService;
    private final FileService fileService;
    private final MailingService mailingService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final Comparator<Announcement> orderByDate = (o1, o2) -> o2.getDate().compareTo(o1.getDate());

    @Autowired
    public CourseController(AuthFacade authFacade, AnnouncementService announcementService, CourseService courseService, FileCategoryService fileCategoryService, FileExtensionService fileExtensionService, FileService fileService, MailingService mailingService) {
        this.authFacade = authFacade;
        this.announcementService = announcementService;
        this.courseService = courseService;
        this.fileCategoryService = fileCategoryService;
        this.fileExtensionService = fileExtensionService;
        this.fileService = fileService;
        this.mailingService = mailingService;
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public String coursePortal(@PathVariable Integer courseId) {
       return "redirect:/course/{courseId}/announcements";

    }

    @RequestMapping(value = "/{courseId}/announcements", method = RequestMethod.GET)
    public ModelAndView announcements(@PathVariable Long courseId, final AnnouncementForm announcementForm,
                                      String successMessage) {
        final ModelAndView mav;
        List<Announcement> announcements = announcementService.listByCourse(courseId, orderByDate);
        if (courseService.isPrivileged(authFacade.getCurrentUser().getUserId(), courseId)) {
            mav = new ModelAndView("teacher/teacher-course");
            mav.addObject("announcementForm", announcementForm);
            mav.addObject("successMessage", successMessage);
        } else {
            mav = new ModelAndView("course");
        }
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("announcementList", announcements);
        mav.addObject("dateTimeFormatter",dateTimeFormatter);
        return mav;
    }

    @RequestMapping(value = "/{courseId}/announcements", method = RequestMethod.POST)
    public ModelAndView postAnnouncement(@PathVariable Long courseId,
                                         @Valid AnnouncementForm announcementForm, final BindingResult errors) {
        String successMessage = null;
        if (!errors.hasErrors()) {
            CampusUser springUser = authFacade.getCurrentUser();
            User currentUser = new User.Builder()
                    .withUserId(springUser.getUserId())
                    .withEmail(springUser.getEmail())
                    .withFileNumber(springUser.getFileNumber())
                    .withPassword(springUser.getPassword())
                    .withName(springUser.getName())
                    .withSurname(springUser.getSurname())
                    .withUsername(springUser.getUsername())
                    .build();
            announcementService.create(announcementForm.getTitle(), announcementForm.getContent(), currentUser, courseService.getById(courseId).get());
            List<User> userList = courseService.getStudents(courseId);
            List<String> emailList = new ArrayList<>();
            userList.forEach(u->emailList.add(u.getEmail()));
            mailingService.sendBroadcastEmail(emailList, "Nuevo anuncio en curso "+announcementForm.getTitle(), announcementForm.getContent(), "text/plain");


            announcementForm.setContent("");
            announcementForm.setTitle("");
            successMessage = "Anuncio publicado exitosamente";
        }
        return announcements(courseId, announcementForm, successMessage);
    }

    @RequestMapping("/{courseId}/teachers")
    public ModelAndView professors(@PathVariable Long courseId) {
        final ModelAndView mav = new ModelAndView("teachers");
        Map<User, Role> teachers = courseService.getTeachers(courseId);
        Set<Map.Entry<User, Role>> teacherSet = teachers.entrySet();
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("teacherSet", teacherSet);
        return mav;
    }

    @RequestMapping(value = "/{courseId}/files", method = RequestMethod.GET)
    public ModelAndView files(@PathVariable Long courseId, final FileForm fileForm, String successMessage,
                              @RequestParam(value = "category-type", required = false, defaultValue = "")
                                      List<Long> categoryType,
                              @RequestParam(value = "extension-type", required = false, defaultValue = "")
                                      List<Long> extensionType,
                              @RequestParam(value = "query", required = false, defaultValue = "")
                                      String query,
                              @RequestParam(value = "order-class", required = false, defaultValue = "NAME")
                                      String orderClass,
                              @RequestParam(value = "order-by", required = false, defaultValue = "DESC")
                                      String orderBy) {

        final List<FileModel> files = fileService.listByCriteria(OrderCriterias.valueOf(orderBy),
                SearchingCriterias.valueOf(orderClass),
                query, extensionType, categoryType, authFacade.getCurrentUser().getUserId(), courseId);
        final ModelAndView mav;
        List<FileCategory> categories = fileCategoryService.getCategories();
        final List<FileExtension> extensions = fileExtensionService.getExtensions();
        if (courseService.isPrivileged(authFacade.getCurrentUser().getUserId(), courseId)) {
            mav = new ModelAndView("teacher/teacher-files");
            mav.addObject("fileForm", fileForm);
            mav.addObject("successMessage", successMessage);
        } else {
            mav = new ModelAndView("course-files");
        }
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("categories", categories);
        mav.addObject("files", files);
        mav.addObject("extensions", extensions);
        mav.addObject("categoryType", categoryType);
        mav.addObject("extensionType", extensionType);
        mav.addObject("query", query);
        mav.addObject("orderBy", orderBy);
        mav.addObject("orderClass", orderClass);
        return mav;
    }

    @RequestMapping(value = "/{courseId}/files", method = RequestMethod.POST)
    public ModelAndView uploadFile(@PathVariable Long courseId, @Valid FileForm fileForm, final BindingResult errors) {
        String successMessage = null;
        if (!errors.hasErrors()) {
            CommonsMultipartFile file = fileForm.getFile();
            String filename = file.getOriginalFilename();
            FileModel newFile = fileService.create(file.getSize(), filename, file.getBytes(),
                    courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
            fileService.addCategory(newFile.getFileId(), fileForm.getCategoryId());
            fileForm.setFile(null);
            fileForm.setCategoryId(null);
            successMessage = "Archivo creado exitosamente";
        }
        return files(courseId, fileForm, successMessage, new ArrayList<>(), new ArrayList<>(), "", "NAME", "DESC");
    }

}
