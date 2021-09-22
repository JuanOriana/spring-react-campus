package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import ar.edu.itba.paw.webapp.exception.FileNotFoundException;
import ar.edu.itba.paw.webapp.form.FileForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class FilesController extends AuthController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private FileCategoryService fileCategoryService;

    @Autowired
    private FileExtensionService fileExtensionService;

    @RequestMapping("/files")
    public ModelAndView files(@RequestParam(value = "category-type", required = false, defaultValue = "")
                                      List<Long> categoryType,
                              @RequestParam(value = "extension-type", required = false, defaultValue = "")
                                          List<Long> extensionType,
                              @RequestParam(value = "query", required = false, defaultValue = "")
                                          String query,
                              @RequestParam(value = "order-class",required = false,defaultValue = "NAME")
                                          String orderClass,
                              @RequestParam(value = "order-by",required = false,defaultValue = "DESC")
                                          String orderBy){
        CampusUser user = authFacade.getCurrentUser();
        final List<FileModel> files = fileService.listByCriteria(OrderCriterias.valueOf(orderBy),
                SearchingCriterias.valueOf(orderClass),
                query,extensionType,categoryType,authFacade.getCurrentUser().getUserId());
        final List<FileCategory> categories = fileCategoryService.getCategories();
        final List<FileExtension> extensions = fileExtensionService.getExtensions();
        ModelAndView mav = new ModelAndView("files");
        mav.addObject("categories",categories);
        mav.addObject("files",files);
        mav.addObject("extensions",extensions);
        mav.addObject("categoryType",categoryType);
        mav.addObject("extensionType",extensionType);
        mav.addObject("query",query);
        mav.addObject("orderBy",orderBy);
        mav.addObject("orderClass",orderClass);
        return mav;
    }

    @RequestMapping(value = "/files/{fileId}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable Long fileId, HttpServletResponse response) {
        FileModel file = fileService.getById(fileId).orElseThrow(FileNotFoundException::new);
        if (!file.getExtension().getFileExtension().equals("pdf"))
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
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

    @RequestMapping(value = "/files/{fileId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteFile(@PathVariable Long fileId) {
        fileService.delete(fileId);
    }
}
