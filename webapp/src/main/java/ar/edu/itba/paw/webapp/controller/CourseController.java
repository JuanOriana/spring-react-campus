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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CourseController extends AuthController{

    @Autowired
    AuthFacade authFacade;

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    CourseService courseService;

    @Autowired
    FileCategoryService fileCategoryService;

    @Autowired
    FileExtensionService fileExtensionService;

    @Autowired
    FileService fileService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    private final Comparator<Announcement> orderByDate = (o1, o2) -> o2.getDate().compareTo(o1.getDate());

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.GET)
    public RedirectView coursePortal(@PathVariable Integer courseId) {
       return new RedirectView("/course/{courseId}/announcements");
    }

    @RequestMapping(value = "/course/{courseId}/announcements", method = RequestMethod.GET)
    public ModelAndView announcements(@PathVariable Integer courseId, final AnnouncementForm announcementForm) {
        final ModelAndView mav;
        List<Announcement> announcements = announcementService.listByCourse(courseId, orderByDate);
        if(courseService.isPrivileged(authFacade.getCurrentUser().getUserId(), courseId)) {
            mav = new ModelAndView("teacher/teacher-course");
            mav.addObject("announcementForm",announcementForm);
        } else {
            mav = new ModelAndView("course");
        }
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("announcementList", announcements);
        return mav;
    }

    @RequestMapping(value = "/course/{courseId}/announcements", method = RequestMethod.POST)
    public ModelAndView postAnnouncement(@PathVariable Integer courseId,
                                             @Valid AnnouncementForm announcementForm, final BindingResult errors){
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
            announcementService.create(new Announcement(LocalDateTime.now(),
                    announcementForm.getTitle(), announcementForm.getContent(), currentUser, courseService.getById(courseId).get()));
            announcementForm.setContent("");
            announcementForm.setTitle("");
        }
        return announcements(courseId, announcementForm);
    }

    @RequestMapping("/course/{courseId}/teachers")
    public ModelAndView professors(@PathVariable Integer courseId) {
        final ModelAndView mav = new ModelAndView("teachers");
        Map<User, Role> teachers = courseService.getTeachers(courseId);
        Set<Map.Entry<User,Role>> teacherSet = teachers.entrySet();
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("teacherSet",teacherSet);
        return mav;
    }

    @RequestMapping(value = "/course/{courseId}/files", method = RequestMethod.GET)
    public ModelAndView files(@PathVariable Integer courseId, final FileForm fileForm) {
        final ModelAndView mav;
        final List<FileModel> files = fileService.getByCourseId(courseId);
        List<FileCategory> categories = fileCategoryService.getCategories();
        final List<FileExtension> extensions = fileExtensionService.getExtensions();
        if(courseService.isPrivileged(authFacade.getCurrentUser().getUserId(), courseId)) {
            mav = new ModelAndView("teacher/teacher-files");
            mav.addObject("fileForm",fileForm);
        } else {
            mav = new ModelAndView("files");
        }
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("categories",categories);
        mav.addObject("files",files);
        mav.addObject("extensions",extensions);
        return mav;
    }

    @RequestMapping(value = "/course/{courseId}/files", method = RequestMethod.POST)
    public ModelAndView uploadFile(@PathVariable Integer courseId,
                                     @Valid FileForm fileForm, final BindingResult errors){
        if (!errors.hasErrors()) {
            CommonsMultipartFile file = fileForm.getFile();
            String filename = file.getOriginalFilename();
            String extension = getExtension(filename);
            FileModel newFile = fileService.create(new FileModel(file.getSize(), filename, LocalDateTime.now(), file.getBytes(),
                    new FileExtension(extension), courseService.getById(courseId).orElseThrow(CourseNotFoundException::new)));
            fileService.addCategory(newFile.getFileId(), fileForm.getCategoryId());
            fileForm.setFile(null);
            fileForm.setCategoryId(null);
        }
        return files(courseId,fileForm);
    }

    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable int fileId, HttpServletResponse response) {
        FileModel file = fileService.getById(fileId).orElseThrow(FileNotFoundException::new);
        if (!file.getFileExtension().getFileExtension().equals("pdf"))
            response.setHeader("Content-Disposition","attachment; filename=\""+ file.getName()+"\"");
        else
            response.setContentType("application/pdf");
        try {
            InputStream is = new ByteArrayInputStream(file.getFile());
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            System.out.println("Error writing file to output stream. Filename was " + file.getName() + ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
    private String getExtension(String filename){
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        return extension;
    }
}
