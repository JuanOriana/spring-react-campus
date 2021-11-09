package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.models.FileCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/file_category_populator.sql")
@Rollback
@Transactional
public class FileCategoryDaoImplTest {

    private static final String FILE_CATEGORY = "exam";
    private static final long FILE_CATEGORY_ID = 1337;

    @Autowired
    private FileCategoryDao fileCategoryDao;


    @Test
    public void testCreate() {
        FileCategory fileCategory = fileCategoryDao.create(FILE_CATEGORY);
        assertNotNull(fileCategory);
    }

    @Test
    public void testUpdate() {
        final String uptadedCategoryName = "Guia Practica";
        assertTrue(fileCategoryDao.update(FILE_CATEGORY_ID, uptadedCategoryName));
    }

    @Test
    public void testDelete() {
        boolean wasDeleted = fileCategoryDao.delete(FILE_CATEGORY_ID);
        assertTrue(wasDeleted);
    }

    @Test
    public void testGetCategories() {
        List<FileCategory> list = fileCategoryDao.getCategories();
        assertEquals(1, list.size());
        FileCategory fileCategory = list.get(0);
        assertEquals(FILE_CATEGORY_ID, fileCategory.getCategoryId());
    }

}
