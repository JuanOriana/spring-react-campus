package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.models.FileExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FileExtensionServiceImpl implements FileExtensionService {

    private final FileExtensionDao fileExtensionDao;

    @Autowired
    public FileExtensionServiceImpl(FileExtensionDao fileExtensionDao) {
        this.fileExtensionDao = fileExtensionDao;
    }

    @Transactional
    @Override
    public FileExtension create(String fileExtension) {
        return fileExtensionDao.create(fileExtension);
    }

    @Transactional
    @Override
    public Optional<FileExtension> findById(Long fileExtensionId) {
        return fileExtensionDao.findById(fileExtensionId);
    }

    @Transactional
    @Override
    public boolean update(long fileExtensionId, String fileExtension) {
        return fileExtensionDao.update(fileExtensionId,fileExtension);
    }

    @Transactional
    @Override
    public boolean delete(long fileExtensionId) {
        return fileExtensionDao.delete(fileExtensionId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileExtension> getExtensions() {
        return fileExtensionDao.getExtensions();
    }
}
