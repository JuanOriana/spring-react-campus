package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.models.FileExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileExtensionServiceImpl implements FileExtensionService {

    private final FileExtensionDao fileExtensionDao;

    @Autowired
    public FileExtensionServiceImpl(FileExtensionDao fileExtensionDao) {
        this.fileExtensionDao = fileExtensionDao;
    }

    @Override
    public FileExtension create(String fileExtension) {
        return fileExtensionDao.create(fileExtension);
    }

    @Override
    public boolean update(long fileExtensionId, String fileExtension) {
        return fileExtensionDao.update(fileExtensionId,fileExtension);
    }

    @Override
    public boolean delete(long fileExtensionId) {
        return fileExtensionDao.delete(fileExtensionId);
    }

    @Override
    public List<FileExtension> getExtensions() {
        return fileExtensionDao.getExtensions();
    }
}
