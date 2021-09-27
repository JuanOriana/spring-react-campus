package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.exception.FileNotFoundException;
import ar.edu.itba.paw.webapp.exception.PaginationException;
import ar.edu.itba.paw.webapp.implementation.AppPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                              @RequestParam(value = "order-property", required = false, defaultValue = "DATE")
                                      String orderProperty,
                              @RequestParam(value = "order-direction", required = false, defaultValue = "DESC")
                                      Sort.Direction orderDirection,
                              @RequestParam(value = "page", required = false, defaultValue = "1")
                                      Integer page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                      Integer pageSize) {
        Page<FileModel> files;
        try {
            files = fileService.findFileByPage(query, extensionType, categoryType,
                    authFacade.getCurrentUser().getUserId(), AppPageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, orderProperty)))
                    .orElseThrow(PaginationException::new);
        } catch (IllegalArgumentException e) {
            throw new PaginationException();
        }
        ModelAndView mav = new ModelAndView("files");
        mav.addObject("categories", fileCategoryService.getCategories());
        mav.addObject("files", files.getContent());
        mav.addObject("extensions", fileExtensionService.getExtensions());
        mav.addObject("categoryType", categoryType);
        mav.addObject("extensionType", extensionType);
        mav.addObject("query", query);
        mav.addObject("orderDirection", orderDirection);
        mav.addObject("orderProperty", orderProperty);
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
            LOGGER.debug(String.format("Error writing file to output stream. Filename was %s", file.getName() + ex));
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @DeleteMapping(value = "/files/{fileId}")
    @ResponseBody
    public void deleteFile(@PathVariable Long fileId) {
        fileService.delete(fileId);
    }
}
