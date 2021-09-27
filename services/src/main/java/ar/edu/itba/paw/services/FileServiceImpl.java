package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;

    private static final int MIN_PAGE_COUNT = 1;
    private static final int MIN_PAGE_SIZE = 1;
    private static final int MAX_PAGE_SIZE = 50;

    @Autowired
    public FileServiceImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public FileModel create(Long size, String name, byte[] file, Course course) {
        return fileDao.create(size, LocalDateTime.now(), name, file, course);
    }

    @Override
    public FileModel create(Long size, String name, byte[] file, Course course, Long fileCategoryId) {
        FileModel fileModel = this.create(size, name, file, course);
        this.addCategory(fileModel.getFileId(), fileCategoryId);
        return fileModel;
    }

    @Override
    public boolean update(Long fileId, FileModel file) {
        return fileDao.update(fileId, file);
    }

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

    @Override
    public boolean addCategory(Long fileId, Long fileCategoryId) {
        return fileDao.addCategory(fileId, fileCategoryId);
    }

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
    public Optional<Page<FileModel>> findFileByPage(String keyword, List<Long> extensions, List<Long> categories,
                                          Long userId, Long courseId, Pageable pageable) {
        if(pageable.getPageSize() < MIN_PAGE_SIZE || pageable.getPageSize() > MAX_PAGE_SIZE ||
           pageable.getPageNumber() < MIN_PAGE_COUNT ||
           pageable.getPageNumber() > fileDao.getPageCount(keyword, extensions, categories, userId, courseId, pageable.getPageSize()))
            return Optional.empty();
        return fileDao.findFileByPage(keyword, extensions, categories, userId, courseId, pageable);
    }

    @Override
    public Optional<Page<FileModel>> findFileByPage(String param, List<Long> extensions, List<Long> categories,
                                          Long userId, Pageable pageable) {
        return findFileByPage(param, extensions, categories, userId, -1L, pageable);
    }

    @Override
    public void incrementDownloads(Long fileId) {
        fileDao.incrementDownloads(fileId);
    }

}
