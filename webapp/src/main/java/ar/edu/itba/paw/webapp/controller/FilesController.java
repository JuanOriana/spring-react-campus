package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import ar.edu.itba.paw.webapp.exception.FileNotFoundException;
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
public class FilesController extends AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesController.class);

    private final FileService fileService;
    private final FileCategoryService fileCategoryService;
    private final FileExtensionService fileExtensionService;

    @Autowired
    public FilesController(FileService fileService, FileCategoryService fileCategoryService,
                           FileExtensionService fileExtensionService,
                           AuthFacade authFacade) {
        super(authFacade);
        this.fileService = fileService;
        this.fileCategoryService = fileCategoryService;
        this.fileExtensionService = fileExtensionService;
    }

    @RequestMapping("/files")
    public ModelAndView files(@RequestParam(value = "category-type", required = false, defaultValue = "")
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
        CampusUser user = authFacade.getCurrentUser();
        CampusPage<FileModel> filePage = fileService.listByUser(query, extensionType, categoryType, user.getUserId(),
                new CampusPageRequest(page, pageSize), new CampusPageSort(orderDirection, orderProperty));
        ModelAndView mav = new ModelAndView("files");
        mav.addObject("categories", fileCategoryService.getCategories());
        mav.addObject("files", filePage.getContent());
        mav.addObject("extensions", fileExtensionService.getExtensions());
        mav.addObject("categoryType", categoryType);
        mav.addObject("extensionType", extensionType);
        mav.addObject("query", query);
        mav.addObject("orderDirection", orderDirection);
        mav.addObject("orderProperty", orderProperty);
        mav.addObject("currentPage", filePage.getPage());
        mav.addObject("maxPage", filePage.getTotal());
        mav.addObject("pageSize", filePage.getSize());
        return mav;
    }

    @GetMapping(value = "/files/{fileId}")
    public void downloadFile(@PathVariable Long fileId, HttpServletResponse response) {
        FileModel file = fileService.getById(fileId).orElseThrow(FileNotFoundException::new);
        if (!file.getExtension().getFileExtensionName().equals("pdf"))
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        else
            response.setContentType("application/pdf");
        try {
            InputStream is = new ByteArrayInputStream(file.getFile());
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            fileService.incrementDownloads(fileId);
        } catch (IOException ex) {
            LOGGER.error(String.format("Error writing file to output stream. Filename was %s", file.getName() + ex));
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @DeleteMapping(value = "/files/{fileId}")
    @ResponseBody
    public void deleteFile(@PathVariable Long fileId) {
        fileService.delete(fileId);
    }
}
