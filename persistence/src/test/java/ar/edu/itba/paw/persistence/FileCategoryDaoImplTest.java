package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.models.FileCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class FileCategoryDaoImplTest extends BasicPopulator {

    @Autowired
    private FileCategoryDao fileCategoryDao;

    private static final RowMapper<FileCategory> FILE_CATEGORY_ROW_MAPPER = (rs, rowNum) -> new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName"));

    @Before
    public void setUp() {
        super.setUp();
        insertFileCategory(FILE_CATEGORY_ID, FILE_CATEGORY);
    }

    @Test
    public void testCreate() {
        FileCategory fileCategory = fileCategoryDao.create(FILE_CATEGORY);
        assertNotNull(fileCategory);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_categories"));
    }

    @Test
    public void testUpdate() {
        String newCategoryName = "Guia Practica";
        boolean wasUpdated = fileCategoryDao.update(FILE_CATEGORY_ID, newCategoryName);
        assertTrue(wasUpdated);

        FileCategory fileCategoryFromDB = jdbcTemplate.query("SELECT * FROM file_categories WHERE categoryId = ?;", new Object[]{FILE_CATEGORY_ID}, FILE_CATEGORY_ROW_MAPPER).get(0);

        assertEquals(FILE_CATEGORY_ID.longValue(), fileCategoryFromDB.getCategoryId());
        assertEquals(newCategoryName, fileCategoryFromDB.getCategoryName());
    }

    @Test
    public void testDelete() {
        boolean wasDeleted = fileCategoryDao.delete(FILE_CATEGORY_ID);
        assertTrue(wasDeleted);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_categories"));
    }

    @Test
    public void testGetCategories() {
        List<FileCategory> list = fileCategoryDao.getCategories();
        assertEquals(1, list.size());

        FileCategory fileCategory = list.get(0);
        assertEquals(FILE_CATEGORY_ID.longValue(), fileCategory.getCategoryId());

    }

}
