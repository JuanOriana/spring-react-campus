package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.models.FileCategory;
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
public class FileCategoryDaoJpa implements FileCategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public FileCategory create(String newCategory) {
        final FileCategory fCategory = new FileCategory(newCategory);
        em.persist(fCategory);
        return fCategory;
    }

    @Transactional
    @Override
    public boolean update(long fileCategoryId, String newFileCategory) {
        Optional<FileCategory> dbFileCategory = Optional.ofNullable(em.find(FileCategory.class, fileCategoryId));
        if(!dbFileCategory.isPresent()) return false;
        dbFileCategory.get().setCategoryName(newFileCategory);
        em.flush();
        return true;
    }

    @Transactional
    @Override
    public boolean delete(long fileCategoryId) {
        Optional<FileCategory> dbFileCategory = Optional.ofNullable(em.find(FileCategory.class, fileCategoryId));
        if(!dbFileCategory.isPresent()) return false;
        em.remove(dbFileCategory.get());
        return true;
    }

    @Override
    public List<FileCategory> getCategories() {
        final TypedQuery<FileCategory> listCategories = em.createQuery("SELECT fc from FileCategory fc", FileCategory.class);
        return listCategories.getResultList();
    }

    @Override
    public Optional<FileCategory> findById(Long categoryId) {
        return Optional.ofNullable(em.find(FileCategory.class, categoryId));
    }
}
