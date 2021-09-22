package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.exception.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*
    Extracted implementation from:
        https://www.baeldung.com/custom-error-page-spring-mvc
 */

@Controller
public class ErrorController {

    @GetMapping(value = "403")
    public ModelAndView render403ErrorPage() {
        return new ModelAndView("403");
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchCourse() {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.addObject("errorMsg", "Course does not exist");
        return mav;
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchFile() {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.addObject("errorMsg", "File does not exist");
        return mav;
    }

    @GetMapping(value = "error")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
            default: {
                errorMsg = "Http Error Code: Undefined";
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
