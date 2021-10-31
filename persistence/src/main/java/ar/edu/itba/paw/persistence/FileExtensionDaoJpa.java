package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileExtension;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class FileExtensionDaoJpa implements FileExtensionDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public FileExtension create(String fileExtension) {
        final FileExtension fExtension = new FileExtension(fileExtension);
        em.persist(fExtension);
        return fExtension;
    }


    @Override
    public boolean update(long fileExtensionId, String fileExtension) {
        Optional<FileExtension> dbFileExtension = Optional.ofNullable(em.find(FileExtension.class, fileExtensionId));
        if(!dbFileExtension.isPresent()) return false;
        dbFileExtension.get().setFileExtensionName(fileExtension);
        return true;
    }


    @Override
    public boolean delete(long fileExtensionId) {
        Optional<FileExtension> dbFileExtension = Optional.ofNullable(em.find(FileExtension.class, fileExtensionId));
        if(!dbFileExtension.isPresent()) return false;
        em.remove(dbFileExtension.get());
        return true;
    }

    @Override
    public List<FileExtension> getExtensions() {
        final TypedQuery<FileExtension> listExtensions = em.createQuery("SELECT fe FROM FileExtension fe", FileExtension.class);
        return listExtensions.getResultList();
    }

    @Override
    public Optional<String> getExtension(Long extensionId) {
        return Optional.ofNullable(em.find(FileCategory.class, extensionId).getCategoryName());
    }
}
