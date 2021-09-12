package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    public FileModel create(FileModel file) {
        return fileDao.create(file);
    }

    @Override
    public boolean update(long fileId, FileModel file) {
        return fileDao.update(fileId, file);
    }

    @Override
    public boolean delete(long fileId) {
        return fileDao.delete(fileId);
    }

    @Override
    public List<FileModel> list() {
        return new ArrayList<>(fileDao.list());
    }

    @Override
    public Optional<FileModel> getById(long fileId) {
        return fileDao.getById(fileId);
    }

    @Override
    public List<FileModel> getByName(String fileName) {
        return fileDao.getByName(fileName);
    }

    @Override
    public List<FileModel> getByExtension(long extensionId) {
        return fileDao.getByExtension(extensionId);
    }

    @Override
    public boolean addCategory(long fileId, long fileCategoryId) {
        return fileDao.addCategory(fileId, fileCategoryId);
    }

    @Override
    public boolean removeCategory(long fileId, long fileCategoryId) {
        return fileDao.removeCategory(fileId, fileCategoryId);
    }

    @Override
    public List<FileCategory> getFileCategories(long fileId) {
        return fileDao.getFileCategories(fileId);
    }

    @Override
    public List<FileModel> getByCategory(long fileCategoryId) {
        return fileDao.getByCategory(fileCategoryId);
    }

    @Override
    public List<FileModel> getByMultipleCategories(List<FileCategory> categories) {
        Set<FileModel> filesContainingCategories = new HashSet<>(getByCategory(categories.get(0).getCategoryId()));
        for (FileCategory cat : categories.subList(1,categories.size())){
            filesContainingCategories = filesContainingCategories.stream().filter(getByCategory(cat.getCategoryId())::contains).collect(Collectors.toSet());
        }
        return new ArrayList<>(filesContainingCategories);
    }

    @Override
    public List<FileModel> getByCourseId(long courseId) {
        return fileDao.getByCourseId(courseId);
    }

}
