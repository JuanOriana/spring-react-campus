package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.FileExtension;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class FileExtensionDaoJpa implements FileExtensionDao {

    @PersistenceContext
    private EntityManager em;

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

    @Override
    public Optional<String> getExtension(Long extensionId) {
        return Optional.empty();
    }
}
