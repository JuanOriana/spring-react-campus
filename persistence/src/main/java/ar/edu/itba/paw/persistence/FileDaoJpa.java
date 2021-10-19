package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class FileDaoJpa implements FileDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public FileModel create(Long size, LocalDateTime date, String name, byte[] file, Course course) {
        return null;
    }

    @Transactional
    @Override
    public boolean update(Long fileId, FileModel file) {
        return false;
    }

    @Transactional
    @Override
    public boolean delete(Long fileId) {
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileModel> list(Long userId) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FileModel> findById(Long fileId) {
        return Optional.empty();
    }

    @Override
    public boolean associateCategory(Long fileId, Long fileCategoryId) {
        TypedQuery<FileModel> queryFileCategory = em.createQuery("SELECT f FROM FileModel f JOIN CategoryFileRelationship cfr WHERE f.fileId = :fileId AND cfr.fileCategory.fileCategoryId = :fileCategoryId", FileModel.class);
        queryFileCategory.setParameter("fileId", fileId);
        queryFileCategory.setParameter("fileCategoryId", fileCategoryId);
        if (queryFileCategory.getResultList().size() == 0){
            final CategoryFileRelationship categoryFileRelationship = new CategoryFileRelationship(em.find(FileCategory.class,fileCategoryId), em.find(FileModel.class,fileId));
            em.persist(categoryFileRelationship);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileCategory> getFileCategories(Long fileId) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileModel> findByCategory(Long fileCategoryId) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileModel> findByCourseId(Long courseId) {
        return null;
    }

    @Override
    public boolean hasAccess(Long fileId, Long userId) {
        return false;
    }

    @Override
    public CampusPage<FileModel> listByCourse(String keyword, List<Long> extensions, List<Long> categories, Long userId, Long courseId, CampusPageRequest pageRequest, CampusPageSort sort) throws PaginationArgumentException {
        return null;
    }

    @Override
    public CampusPage<FileModel> listByUser(String keyword, List<Long> extensions, List<Long> categories, Long userId, CampusPageRequest pageRequest, CampusPageSort sort) throws PaginationArgumentException {
        return null;
    }

    @Override
    public void incrementDownloads(Long fileId) {

    }
}
