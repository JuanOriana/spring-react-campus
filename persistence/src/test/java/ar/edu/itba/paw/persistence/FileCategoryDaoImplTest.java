package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.models.FileCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
@Transactional
public class FileCategoryDaoImplTest {

    private static final String FILE_CATEGORY = "exam";
    private static final long FILE_CATEGORY_ID = 1L;

    @Autowired
    private FileCategoryDao fileCategoryDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testCreate() {
        FileCategory fileCategory = fileCategoryDao.create(FILE_CATEGORY);
        assertNotNull(fileCategory);
    }

    @Test
    public void testUpdate() {
        FileCategory fileCategory = fileCategoryDao.create(FILE_CATEGORY);
        String newCategoryName = "Guia Practica";
        boolean wasUpdated = fileCategoryDao.update(fileCategory.getCategoryId(), newCategoryName);
        assertTrue(wasUpdated);
        Optional<FileCategory> fileCategoryUpdated = fileCategoryDao.findById(fileCategory.getCategoryId());
        assertTrue(fileCategoryUpdated.isPresent());
        assertEquals(fileCategory.getCategoryId(), fileCategoryUpdated.get().getCategoryId());
        assertEquals(newCategoryName, fileCategoryUpdated.get().getCategoryName());
    }

    @Test
    public void testDelete() {
        FileCategory fileCategory = fileCategoryDao.create(FILE_CATEGORY);
        boolean wasDeleted = fileCategoryDao.delete(fileCategory.getCategoryId());
        assertTrue(wasDeleted);
    }

    @Test
    public void testGetCategories() {
        fileCategoryDao.create(FILE_CATEGORY);
        List<FileCategory> list = fileCategoryDao.getCategories();
        assertEquals(1, list.size());
        FileCategory fileCategory = list.get(0);
        assertEquals(FILE_CATEGORY_ID, fileCategory.getCategoryId());

    }

}
