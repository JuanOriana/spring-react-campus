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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private JdbcTemplate jdbcTemplate;


    private final Long FILE_EXTENSION_ID = 1L;
    private final String FILE_EXTENSION = "pdf";

    private static final RowMapper<FileExtension> FILE_EXTENSION_ROW_MAPPER = (rs, rowNum) -> new FileExtension(rs.getLong("fileExtensionId"), rs.getString("fileExtension"));

    private void insertFileExtension(Long fileExtensionId,String fileExtension){
        SimpleJdbcInsert fileExtensionsJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_extensions");

        Map<String,Object> args = new HashMap<>();
        args.put("fileExtensionId", fileExtensionId);
        args.put("fileExtension", fileExtension);

        fileExtensionsJdbcInsert.execute(args);
    }


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        insertFileExtension(FILE_EXTENSION_ID, FILE_EXTENSION);
    }

    @Test
    public void testCreate(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "file_extensions");
        FileExtension fileExtension = fileExtensionDao.create(FILE_EXTENSION);
        assertNotNull(fileExtension);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_extensions"));
    }

    @Test
    public void testUpdate(){
        String newExtension = "doc";
        boolean wasUpdated = fileExtensionDao.update(FILE_EXTENSION_ID, newExtension);
        assertTrue(wasUpdated);

        FileExtension fileExtensionFromDB = jdbcTemplate.query("SELECT * FROM file_extensions WHERE fileExtensionId = ?",new Object[]{FILE_EXTENSION_ID},FILE_EXTENSION_ROW_MAPPER).get(0);

        assertEquals(FILE_EXTENSION_ID, fileExtensionFromDB.getFileExtensionId());
        assertEquals(newExtension, fileExtensionFromDB.getFileExtensionName());
    }

    @Test
    public void testDelete(){
        boolean wasDeleted = fileExtensionDao.delete(FILE_EXTENSION_ID);
        assertTrue(wasDeleted);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "file_extensions"));
    }

    @Test
    public void testGetCategories(){
        List<FileExtension> list = fileExtensionDao.getExtensions();
        assertEquals(1, list.size());

        FileExtension fileExtension = list.get(0);
        assertEquals(FILE_EXTENSION_ID, fileExtension.getFileExtensionId());

    }
}
