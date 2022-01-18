package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/course/{courseId}")
public class CourseControllerEE extends AuthController {
    private final AnnouncementService announcementService;
    private final CourseService courseService;
    private final FileCategoryService fileCategoryService;
    private final FileExtensionService fileExtensionService;
    private final TimetableService timetableService;
    private final FileService fileService;
    private final UserService userService;
    private final MailingService mailingService;
    private final ExamService examService;
    private final AnswerService answerService;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseControllerEE.class);

    @Autowired
    public CourseControllerEE(AuthFacade authFacade, AnnouncementService announcementService,
                              CourseService courseService, FileCategoryService fileCategoryService,
                              FileExtensionService fileExtensionService, FileService fileService,
                              UserService userService, MailingService mailingService,
                              TimetableService timetableService, ExamService examService, AnswerService answerService) {
        super(authFacade);
        this.announcementService = announcementService;
        this.courseService = courseService;
        this.fileCategoryService = fileCategoryService;
        this.fileExtensionService = fileExtensionService;
        this.fileService = fileService;
        this.userService = userService;
        this.mailingService = mailingService;
        this.timetableService = timetableService;
        this.examService = examService;
        this.answerService = answerService;
    }

    @ModelAttribute
    public void getCourse(Model model, @PathVariable Long courseId) {
        model.addAttribute("course",
                courseService.findById(courseId).orElseThrow(CourseNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String coursePortal(@PathVariable Long courseId) {
        return "redirect:/course/" + courseId + "/announcements";

    }

    @RequestMapping(method = RequestMethod.GET, value = "/announcements")
    public ModelAndView announcements(@PathVariable Long courseId, final AnnouncementForm announcementForm,
                                      @RequestParam(value = "page", required = false, defaultValue = "1")
                                              Integer page,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                              Integer pageSize) {
        final ModelAndView mav;
        if (courseService.isPrivileged(authFacade.getCurrentUser().getUserId(), courseId)) {
            mav = new ModelAndView("teacher/teacher-course");
            mav.addObject("announcementForm", announcementForm);
        } else {
            mav = new ModelAndView("course");
        }
        CampusPage<Announcement> announcements = announcementService.listByCourse(courseId, page, pageSize);
        mav.addObject("announcementList", announcements.getContent());
        mav.addObject("dateTimeFormatter", dateTimeFormatter);
        mav.addObject("currentPage", announcements.getPage());
        mav.addObject("maxPage", announcements.getTotal());
        mav.addObject("pageSize", announcements.getSize());
        return mav;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/announcements")
    public ModelAndView postAnnouncement(@PathVariable Long courseId,
                                         @Valid AnnouncementForm announcementForm, final BindingResult errors,
                                         RedirectAttributes redirectAttributes) {
        if (!errors.hasErrors()) {
            Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
            // Inject this via @Configuration in the future, no need to build the url each time the endpoint is reached
            final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            Announcement announcement = announcementService.create(announcementForm.getTitle(), announcementForm.getContent(),
                    authFacade.getCurrentUser(), course, baseUrl + "/course/" + course.getCourseId());
            LOGGER.debug("Announcement in course {} created with id: {}", courseId, announcement.getAnnouncementId());
            redirectAttributes.addFlashAttribute("successMessage", "announcement.success.message");
            return new ModelAndView("redirect:/course/" + courseId + "/announcements");
        }
        return announcements(courseId, announcementForm, DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/teachers")
    public ModelAndView teachers(@PathVariable Long courseId) {
        final ModelAndView mav = new ModelAndView("teachers");
        mav.addObject("teacherSet", courseService.getPrivilegedUsers(courseId).entrySet());
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/schedule")
    public ModelAndView schedule(@PathVariable Long courseId) {
        Timetable[] times = timetableService.findByIdOrdered(courseId);
        ModelAndView mav = new ModelAndView("course-schedule");
        mav.addObject("times", times);
        mav.addObject("days", days);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exams")
    public ModelAndView exams(@PathVariable Long courseId, final CreateExamForm createExamForm) {
        ModelAndView mav;
        if (courseService.isPrivileged(authFacade.getCurrentUser().getUserId(), courseId)) {
            mav = new ModelAndView("teacher/teacher-exams");
            mav.addObject("createExamForm", createExamForm);
            mav.addObject("exams", examService.getExamsAndTotals(courseId));
            mav.addObject("minDateTime", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").format(LocalDateTime.now()));
            mav.addObject("examsToAverage", examService.getExamsAverage(courseId));
            mav.addObject("decimalFormat", decimalFormat);

        } else {
            mav = new ModelAndView("course-exams");
            final Long userId = authFacade.getCurrentUser().getUserId();
            mav.addObject("unresolvedExams", examService.getUnresolvedExams(userId, courseId));
            mav.addObject("answerMarks", answerService.getMarks(userId, courseId));
            mav.addObject("average", String.format("%.2f", answerService.getAverageOfUserInCourse(userId, courseId)));

        }
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/exams")
    public ModelAndView exams(@PathVariable Long courseId,
                              @Valid CreateExamForm createExamForm, final BindingResult errors,
                              RedirectAttributes redirectAttributes) {
        if (!errors.hasErrors()) {
            Exam exam = examService.create(courseId, createExamForm.getTitle(), createExamForm.getContent(),
                    createExamForm.getFile().getOriginalFilename(), createExamForm.getFile().getBytes(),
                    createExamForm.getFile().getSize(), LocalDateTime.parse(createExamForm.getStartTime()),
                    LocalDateTime.parse(createExamForm.getEndTime()));
            LOGGER.debug("Exam in course {} created with id: {}", courseId, exam.getExamId());
            redirectAttributes.addFlashAttribute("successMessage", "exam.success.message");
            return new ModelAndView("redirect:/course/" + courseId + "/exams");
        }
        return exams(courseId, createExamForm);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exam/{examId}")
    public ModelAndView exam(@PathVariable Long courseId, @PathVariable Long examId, final SolveExamForm solveExamForm,
                             @RequestParam(value = "page", required = false, defaultValue = "1")
                                     Integer page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                     Integer pageSize,
                             @RequestParam(value = "filter-by", required = false, defaultValue = "")
                                     String filterBy) {
        ModelAndView mav;
        final Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);
        if (courseService.isPrivileged(authFacade.getCurrentUser().getUserId(), courseId)) {
            mav = new ModelAndView("teacher/correct-exam");
            CampusPage<Answer> answers = answerService.getFilteredAnswers(examId, filterBy, page, pageSize);
            mav.addObject("dateTimeFormatter", dateTimeFormatter);
            mav.addObject("exam", exam);
            mav.addObject("answers", answers.getContent());
            mav.addObject("currentPage", answers.getPage());
            mav.addObject("maxPage", answers.getTotal());
            mav.addObject("pageSize", answers.getSize());
            mav.addObject("filterBy", filterBy);
            mav.addObject("average", String.format("%.2f", examService.getAverageScoreOfExam(examId)));

        } else {
            mav = new ModelAndView("solve-exam");
            mav.addObject("solveExamForm", solveExamForm);
            mav.addObject("exam", exam);
            Instant instant = exam.getEndTime().atZone(ZoneId.systemDefault()).toInstant();
            mav.addObject("millisToEnd", instant.toEpochMilli());

        }
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/exam/{examId}")
    public ModelAndView exam(@PathVariable Long courseId, @PathVariable Long examId,
                             @Valid SolveExamForm solveExamForm, final BindingResult errors,
                             RedirectAttributes redirectAttributes) {
        if (!errors.hasErrors()) {
            LocalDateTime now = LocalDateTime.now();
            Answer answer = answerService.updateEmptyAnswer(examId, authFacade.getCurrentUser(), solveExamForm.getExam().getOriginalFilename(), solveExamForm.getExam().getBytes(),
                    solveExamForm.getExam().getSize(), now);
            LOGGER.debug("Answer in course {} created with id: {} at {}", courseId, answer.getAnswerId(), now);
            redirectAttributes.addFlashAttribute("successMessage", "exam.success.message");
            return new ModelAndView("redirect:/course/" + courseId + "/exams");
        }
        return exam(courseId, examId, solveExamForm, DEFAULT_PAGE, DEFAULT_PAGE_SIZE, "");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/exam/{examId}")
    @ResponseBody
    public void deleteExam(@PathVariable Long courseId, @PathVariable Long examId) {
        LOGGER.debug("Deleting exam {}", examId);
        examService.delete(examId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exam/{examId}/answer/{answerId}/correct")
    public ModelAndView correctAnswer(@PathVariable Long courseId, @PathVariable Long examId,
                                      @PathVariable Long answerId, final AnswerCorrectionForm answerCorrectionForm) {
        ModelAndView mav = new ModelAndView("teacher/correct-specific-exam");
        mav.addObject("exam", examService.findById(examId).orElseThrow(ExamNotFoundException::new));
        mav.addObject("answer", answerService.findById(answerId).orElseThrow(ExamNotFoundException::new));
        mav.addObject("answerCorrectionForm", answerCorrectionForm);
        return mav;


    }

    @RequestMapping(method = RequestMethod.POST, value = "/exam/{examId}/answer/{answerId}/correct")
    public ModelAndView correctAnswer(@PathVariable Long courseId, @PathVariable Long examId,
                                      @PathVariable Long answerId, @Valid AnswerCorrectionForm answerCorrectionForm,
                                      final BindingResult errors,
                                      RedirectAttributes redirectAttributes) {
        if (!errors.hasErrors()) {
            LOGGER.debug("Correcting answer {}", answerId);
            answerService.correctExam(answerId, authFacade.getCurrentUser(), answerCorrectionForm.getMark(), answerCorrectionForm.getComments());
            redirectAttributes.addFlashAttribute("successMessage", "answer.solve.success.message");
            return new ModelAndView("redirect:/course/" + courseId + "/exam/" + examId);
        }
        return correctAnswer(courseId, examId, answerId, answerCorrectionForm);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/exam/{examId}/answer/{answerId}/undo-correct")
    public ModelAndView undoCorrectAnswer(@PathVariable Long courseId, @PathVariable Long examId,
                                          @PathVariable Long answerId) {
        LOGGER.debug("Undoing correction of answer {}", answerId);
        answerService.undoExamCorrection(answerId);
        return new ModelAndView("redirect:/course/" + courseId + "/exam/" + examId);

    }


    @RequestMapping(method = RequestMethod.GET, value = "/files")
    public ModelAndView files(@PathVariable Long courseId, final FileForm fileForm,
                              @RequestParam(value = "category-type", required = false, defaultValue = "")
                                      List<Long> categoryType,
                              @RequestParam(value = "extension-type", required = false, defaultValue = "")
                                      List<Long> extensionType,
                              @RequestParam(value = "query", required = false, defaultValue = "")
                                      String query,
                              @RequestParam(value = "order-property", required = false, defaultValue = "date")
                                      String orderProperty,
                              @RequestParam(value = "order-direction", required = false, defaultValue = "desc")
                                      String orderDirection,
                              @RequestParam(value = "page", required = false, defaultValue = "1")
                                      Integer page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                      Integer pageSize) {
        List<FileCategory> fileCategoryList = fileCategoryService.getCategories();
        List<FileExtension> fileExtensionList = fileExtensionService.getExtensions();

        List<FileCategory> listOfAppliedCategoryFilters = new ArrayList<>();
        for (Long categoryId : categoryType) {
            if (categoryId >= 0) {
                listOfAppliedCategoryFilters.add(fileCategoryList.get(categoryId.intValue()));
            }
        }
        List<FileExtension> listOfAppliedExtensionFilters = new ArrayList<>();
        for (Long extensionId : extensionType) {
            if (extensionId >= 0) {
                listOfAppliedExtensionFilters.add(fileExtensionList.get(extensionId.intValue()));
            }
        }

        CampusPage<FileModel> filePage = fileService.listByCourse(query, extensionType, categoryType,
                authFacade.getCurrentUserId(), courseId, page, pageSize, orderDirection, orderProperty);
        final ModelAndView mav;
        if (courseService.isPrivileged(authFacade.getCurrentUserId(), courseId)) {
            mav = new ModelAndView("teacher/teacher-files");
            mav.addObject("fileForm", fileForm);
        } else {
            mav = new ModelAndView("course-files");
        }
        mav.addObject("categories", fileCategoryList);
        mav.addObject("files", filePage.getContent());
        mav.addObject("extensions", fileExtensionList);
        mav.addObject("currentPage", filePage.getPage());
        mav.addObject("maxPage", filePage.getTotal());
        mav.addObject("pageSize", filePage.getSize());
        mav.addObject("filteredCategories", listOfAppliedCategoryFilters);
        mav.addObject("filteredExtensions", listOfAppliedExtensionFilters);
        return FilesControllerEE.loadFileParamsIntoModel(categoryType, extensionType, query, orderProperty, orderDirection, filePage, mav);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/files")
    public ModelAndView uploadFile(@PathVariable Long courseId, @Valid FileForm fileForm, final BindingResult errors,
                                   RedirectAttributes redirectAttributes) {
        if (!errors.hasErrors()) {
            CommonsMultipartFile file = fileForm.getFile();
            // Function is expanded already for multiple categories in the future, passing only one for now
            FileModel createdFile = fileService.create(file.getSize(), file.getOriginalFilename(), file.getBytes(),
                    courseService.findById(courseId).orElseThrow(CourseNotFoundException::new),
                    Collections.singletonList(fileForm.getCategoryId()));
            LOGGER.debug("File in course {} created with id: {}", courseId, createdFile.getFileId());
            redirectAttributes.addFlashAttribute("successMessage", "file.success.message");
            return new ModelAndView("redirect:/course/" + courseId + "/files");
        }
        return files(courseId, fileForm, new ArrayList<>(),
                new ArrayList<>(), "", "date", "desc", DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/mail/{userId}")
    public ModelAndView sendMail(@PathVariable final Long courseId, @PathVariable final Long userId,
                                 final MailForm mailForm) {
        if (!courseService.belongs(userId, courseId) && !courseService.isPrivileged(userId, courseId)) {
            LOGGER.warn("User of id {} does not exist or is not a teacher in {}", userId, courseId);
            throw new UserNotFoundException();
        }
        ModelAndView mav = new ModelAndView("sendmail");
        mav.addObject("user", userService.findById(userId).orElseThrow(UserNotFoundException::new));
        mav.addObject("mailForm", mailForm);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mail/{userId}")
    public ModelAndView sendMail(@PathVariable Long courseId, @PathVariable Long userId,
                                 @Valid MailForm mailForm, final BindingResult errors,
                                 RedirectAttributes redirectAttributes) {
        if (!courseService.belongs(userId, courseId) && !courseService.isPrivileged(userId, courseId)) {
            LOGGER.warn("User of id {} does not exist or is not a teacher in {}", userId, courseId);
            throw new UserNotFoundException();
        }
        if (!errors.hasErrors()) {
            LOGGER.debug("Attempting to send mail to {}", userId);

            mailingService.sendEmail(authFacade.getCurrentUser(), userId, mailForm.getSubject(),
                    mailForm.getContent(), courseId, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("successMessage", "email.success.message");
            return new ModelAndView("redirect:/course/{courseId}/teachers");
        }
        return sendMail(courseId, userId, mailForm);
    }

}
