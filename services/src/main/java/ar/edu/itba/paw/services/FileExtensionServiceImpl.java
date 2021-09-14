package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.models.FileExtension;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileExtensionServiceImpl implements FileExtensionService {

    @Override
    public FileExtension create(String fileExtension) {
        return null;
    }

    @Override
    public boolean update(long fileExtensionId, String fileExtension) {
        return false;
    }

    @Override
    public boolean delete(long fileExtensionId) {
        return false;
    }

    @Override
    public List<FileExtension> getExtensions() {
        return null;
    }
}
