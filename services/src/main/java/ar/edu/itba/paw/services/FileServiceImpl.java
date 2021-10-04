package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;

    private final FileCategoryDao fileCategoryDao;

    @Autowired
    public FileServiceImpl(FileDao fileDao, FileCategoryDao fileCategoryDao) {
        this.fileDao = fileDao;
        this.fileCategoryDao = fileCategoryDao;
    }

    @Transactional
    @Override
    public FileModel create(Long size, String name, byte[] file, Course course) {
        return fileDao.create(size, LocalDateTime.now(), name, file, course);
    }

    @Transactional
    @Override
    public FileModel create(Long size, String name, byte[] file, Course course, List<Long> categories) {
        FileModel fileModel = this.create(size, name, file, course);
        fileModel.setCategories(associateCategories(fileModel.getFileId(), categories));
        return fileModel;
    }

    @Transactional
    @Override
    public boolean update(Long fileId, FileModel file) {
        return fileDao.update(fileId, file);
    }

    @Transactional
    @Override
    public boolean delete(Long fileId) {
        return fileDao.delete(fileId);
    }

    @Override
    public List<FileModel> list(Long userId) {
        return new ArrayList<>(fileDao.list(userId));
    }

    @Override
    public Optional<FileModel> getById(Long fileId) {
        return fileDao.getById(fileId);
    }



    @Transactional
    public List<FileCategory> associateCategories(Long fileId, List<Long> categories) {
        List<FileCategory> fileCategories = new ArrayList<>();
        categories.forEach(queriedId -> {
           fileDao.associateCategory(fileId, queriedId);
           fileCategoryDao.getById(queriedId).ifPresent(fileCategories::add);
        });
        return fileCategories;
    }

    @Override
    public boolean hasAccess(Long fileId, Long userId) {
        return fileDao.hasAccess(fileId, userId);
    }

    @Override
    public CampusPage<FileModel> listByUser(String keyword, List<Long> extensions, List<Long> categories,
                                            Long userId, Integer page, Integer pageSize, String direction,
                                            String property) {
        CampusPage<FileModel> campusPage = fileDao.listByUser(keyword, extensions,
                categories, userId, new CampusPageRequest(page, pageSize),
                new CampusPageSort(direction, property));
        List<FileModel> files = campusPage.getContent();
        files.forEach(f -> f.setCategories(fileDao.getFileCategories(f.getFileId())));
        return campusPage;
    }

    @Override
    public CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions, List<Long> categories,
                                              Long userId, Long courseId, Integer page, Integer pageSize,
                                              String direction, String property) {
        CampusPage<FileModel> campusPage = fileDao.listByCourse(keyword, extensions,
                categories, userId, courseId, new CampusPageRequest(page, pageSize),
                new CampusPageSort(direction, property));
        List<FileModel> files = campusPage.getContent();
        files.forEach(f -> f.setCategories(fileDao.getFileCategories(f.getFileId())));
        return campusPage;
    }

    @Transactional
    @Override
    public void incrementDownloads(Long fileId) {
        fileDao.incrementDownloads(fileId);
    }


}
