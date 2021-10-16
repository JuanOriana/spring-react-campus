package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.models.FileCategory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class FileCategoryDaoJpa implements FileCategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public FileCategory create(String newCategory) {
        return null;
    }

    @Override
    public boolean update(long fileCategoryId, String newFileCategory) {
        return false;
    }

    @Override
    public boolean delete(long fileCategoryId) {
        return false;
    }

    @Override
    public List<FileCategory> getCategories() {
        return null;
    }

    @Override
    public Optional<FileCategory> findById(Long categoryId) {
        return Optional.empty();
    }
}
