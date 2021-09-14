package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileCategoryDao;
import ar.edu.itba.paw.models.FileCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class FileCategoryDaoImplTest{

    @Autowired
    private DataSource ds;

    @Autowired
    private FileCategoryDao fileCategoryDao;

    private static final RowMapper<FileCategory> FILE_CATEGORY_ROW_MAPPER = (rs, rowNum) -> {
        return new FileCategory(rs.getLong("categoryId"), rs.getString("categoryName"));
    };

    private final int FILE_CATEGORY_ID = 1;
    private final String FILE_CATEGORY = "Guia teorica";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert JdbcInsert;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_categories");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "file_categories");
        jdbcTemplate.execute(String.format("INSERT INTO file_categories VALUES (%d, '%s')", FILE_CATEGORY_ID, FILE_CATEGORY));
    }

    @Test
    public void testCreate(){
        FileCategory fileCategory = fileCategoryDao.create(FILE_CATEGORY);
        assertNotNull(fileCategory);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_categories"));
    }

    @Test
    public void testUpdate(){
        String newCategoryName = "Guia Practica";
        boolean wasUpdated = fileCategoryDao.update(FILE_CATEGORY_ID, newCategoryName);
        assertTrue(wasUpdated);

        String sqlGetFileOfId = String.format("SELECT * FROM file_categories WHERE categoryId = %d;", FILE_CATEGORY_ID);
        FileCategory fileCategoryFromDB = jdbcTemplate.query(sqlGetFileOfId,FILE_CATEGORY_ROW_MAPPER).get(0);

        assertEquals(FILE_CATEGORY_ID, fileCategoryFromDB.getCategoryId());
        assertEquals(newCategoryName, fileCategoryFromDB.getCategoryName());
    }

    @Test
    public void testDelete(){
        boolean wasDeleted = fileCategoryDao.delete(FILE_CATEGORY_ID);
        assertTrue(wasDeleted);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_categories"));
    }

    @Test
    public void testGetCategories(){
        List<FileCategory> list = fileCategoryDao.getCategories();
        assertEquals(1, list.size());

        FileCategory fileCategory = list.get(0);
        assertEquals(FILE_CATEGORY_ID, fileCategory.getCategoryId());

    }

}
