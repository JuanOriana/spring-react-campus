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

    @Override
    public List<FileModel> getByName(String fileName) {
        return fileDao.getByName(fileName);
    }

    @Override
    public List<FileModel> getByExtension(Long extensionId) {
        return fileDao.getByExtension(extensionId);
    }

    @Override
    public List<FileModel> getByExtension(String extension) {
        return fileDao.getByExtension(extension);
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

    @Transactional
    @Override
    public boolean removeCategory(Long fileId, Long fileCategoryId) {
        return fileDao.removeCategory(fileId, fileCategoryId);
    }

    @Override
    public List<FileCategory> getFileCategories(Long fileId) {
        return fileDao.getFileCategories(fileId);
    }

    @Override
    public List<FileModel> getByCategory(Long fileCategoryId) {
        return fileDao.getByCategory(fileCategoryId);
    }

    @Override
    public List<FileModel> getByMultipleCategories(List<FileCategory> categories) {
        Set<FileModel> filesContainingCategories = new HashSet<>(getByCategory(categories.get(0).getCategoryId()));
        for (FileCategory cat : categories.subList(1, categories.size())) {
            filesContainingCategories = filesContainingCategories.stream().filter(getByCategory(cat.getCategoryId())::contains).collect(Collectors.toSet());
        }
        return new ArrayList<>(filesContainingCategories);
    }

    @Override
    public List<FileModel> getByCourseId(Long courseId) {
        return fileDao.getByCourseId(courseId);
    }

    @Override
    public boolean hasAccess(Long fileId, Long userId) {
        return fileDao.hasAccess(fileId, userId);
    }

    @Override
    public CampusPage<FileModel> listByUser(String keyword, List<Long> extensions, List<Long> categories,
                                            Long userId, CampusPageRequest pageRequest,
                                            CampusPageSort sort) {
        CampusPage<FileModel> campusPage = fileDao.listByUser(keyword, extensions,
                categories, userId, pageRequest, sort);
        List<FileModel> files = campusPage.getContent();
        files.forEach(f -> f.setCategories(fileDao.getFileCategories(f.getFileId())));
        return campusPage;
    }

    @Override
    public CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions, List<Long> categories,
                                              Long userId, Long courseId, CampusPageRequest pageRequest,
                                              CampusPageSort sort) {
        CampusPage<FileModel> campusPage = fileDao.listByCourse(keyword, extensions,
                categories, userId, courseId, pageRequest, sort);
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
