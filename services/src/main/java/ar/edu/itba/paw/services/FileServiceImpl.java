package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

}
