package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileService;
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

    //TODO: method to get file by its name
}
