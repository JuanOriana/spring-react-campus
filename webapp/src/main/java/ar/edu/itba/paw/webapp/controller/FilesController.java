package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FilesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);

    @Autowired
    FileService fileService;

    @Autowired
    FileCategoryService fileCategoryService;

    @RequestMapping("/files")
    public ModelAndView files(){
        final List<FileModel> files = fileService.list();
        final List<FileCategory> categories = fileCategoryService.getCategories();
        ModelAndView mav = new ModelAndView("files");
        mav.addObject("files",files);
        mav.addObject("categories",categories);
        return mav;
    }
}
