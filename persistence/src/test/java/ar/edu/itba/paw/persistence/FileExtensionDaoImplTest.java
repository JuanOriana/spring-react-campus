package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.FileExtension;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class FileExtensionDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private FileExtensionDao fileExtensionDao;

    private static final RowMapper<FileExtension> FILE_EXTENSION_ROW_MAPPER = (rs, rowNum) -> {
        return new FileExtension(rs.getLong("fileExtensionId"), rs.getString("fileExtension"));
    };

    private final Long FILE_EXTENSION_ID = 1L;
    private final String FILE_EXTENSION = "pdf";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert JdbcInsert;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_extensions");
    }

    @Test
    public void testCreate(){
        FileExtension fileExtension = fileExtensionDao.create(FILE_EXTENSION);
        assertNotNull(fileExtension);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_extensions"));
    }

    @Test
    public void testUpdate(){
        jdbcTemplate.execute(String.format("INSERT INTO file_extensions VALUES (%d, '%s')", FILE_EXTENSION_ID, FILE_EXTENSION));
        String newExtension = "doc";
        boolean wasUpdated = fileExtensionDao.update(FILE_EXTENSION_ID, newExtension);
        assertTrue(wasUpdated);

        String sqlGetFileOfId = String.format("SELECT * FROM file_extensions WHERE fileExtensionId = %d;", FILE_EXTENSION_ID);
        FileExtension fileExtensionFromDB = jdbcTemplate.query(sqlGetFileOfId,FILE_EXTENSION_ROW_MAPPER).get(0);

        assertEquals(FILE_EXTENSION_ID, fileExtensionFromDB.getFileExtensionId());
        assertEquals(newExtension, fileExtensionFromDB.getFileExtensionName());
    }

    @Test
    public void testDelete(){
        jdbcTemplate.execute(String.format("INSERT INTO file_extensions VALUES (%d, '%s')", FILE_EXTENSION_ID, FILE_EXTENSION));
        boolean wasDeleted = fileExtensionDao.delete(FILE_EXTENSION_ID);
        assertTrue(wasDeleted);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_extensions"));
    }

    @Test
    public void testGetCategories(){
        jdbcTemplate.execute(String.format("INSERT INTO file_extensions VALUES (%d, '%s')", FILE_EXTENSION_ID, FILE_EXTENSION));
        List<FileExtension> list = fileExtensionDao.getExtensions();
        assertEquals(1, list.size());

        FileExtension fileExtension = list.get(0);
        assertEquals(FILE_EXTENSION_ID, fileExtension.getFileExtensionId());

    }
}
