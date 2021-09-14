package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.FileExtension;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileExtensionDaoImpl implements FileExtensionDao {

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
