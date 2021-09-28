package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import ar.edu.itba.paw.webapp.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.exception.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchCourse() {
        return new ModelAndView("404");
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchFile() {
        return new ModelAndView("404");
    }

    @ExceptionHandler(PaginationArgumentException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView invalidRequestParameter() {
        return new ModelAndView("404");
    }
}
