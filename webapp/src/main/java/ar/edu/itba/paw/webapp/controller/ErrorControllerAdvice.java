package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.exception.DuplicateCourseException;
import ar.edu.itba.paw.models.exception.DuplicateUserException;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import ar.edu.itba.paw.models.exception.SystemUnavailableException;
import ar.edu.itba.paw.webapp.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.exception.FileNotFoundException;
import ar.edu.itba.paw.webapp.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.CourseForm;
import ar.edu.itba.paw.webapp.form.UserRegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchCourse() {
        return new ModelAndView("404");
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ModelAndView userKeyViolation(DuplicateUserException ex) {
        ModelAndView mav = new ModelAndView("admin/new-user");
        mav.addObject("userRegisterForm", new UserRegisterForm());
        mav.addObject("isUsernameDuplicated", ex.isUsernameDuplicated());
        mav.addObject("isFileNumberDuplicated", ex.isFileNumberDuplicated());
        mav.addObject("isEmailDuplicated", ex.isEmailDuplicated());
        return mav;
    }

    @ExceptionHandler(DuplicateCourseException.class)
    public ModelAndView courseKeyViolation() {
        ModelAndView mav = new ModelAndView("admin/new-course");
        final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        mav.addObject("courseForm", new CourseForm());
        mav.addObject("isCourseDuplicated", true);
        mav.addObject("days",days);
        return mav;
    }

    @ExceptionHandler(SystemUnavailableException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView unexpectedDatabaseError(SystemUnavailableException ex) {
        LOGGER.error(ex.getErrorMessage());
        return new ModelAndView("500");
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchFile() {
        return new ModelAndView("404");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchUser() {
        return new ModelAndView("404");
    }

    @ExceptionHandler(PaginationArgumentException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView invalidRequestParameter() {
        return new ModelAndView("404");
    }
}
