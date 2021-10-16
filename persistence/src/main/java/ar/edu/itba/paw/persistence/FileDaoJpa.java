package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

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

    @Override
    public boolean update(Long fileId, FileModel file) {
        return false;
    }

    @Override
    public boolean delete(Long fileId) {
        return false;
    }

    @Override
    public List<FileModel> list(Long userId) {
        return null;
    }

    @Override
    public Optional<FileModel> findById(Long fileId) {
        return Optional.empty();
    }

    @Override
    public boolean associateCategory(Long fileId, Long fileCategoryId) {
        return false;
    }

    @Override
    public List<FileCategory> getFileCategories(Long fileId) {
        return null;
    }

    @Override
    public List<FileModel> findByCategory(Long fileCategoryId) {
        return null;
    }

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
