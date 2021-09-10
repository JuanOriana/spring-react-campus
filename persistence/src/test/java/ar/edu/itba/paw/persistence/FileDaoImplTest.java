package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileExtensionModel;
import ar.edu.itba.paw.models.FileModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class FileDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private FileDao fileDao;

    private JdbcTemplate jdbcTemplate;

    // FileCategory
    private final int CATEGORY_ID = 1;
    private final String CATEGORY_NAME = "Guia Practica";

    // FileExtension
    private final int FILE_EXTENSION_ID = 1;
    private final String FILE_EXTENSION = "pdf";

    // FileModel
    private final int FILE_ID = 1;

    private FileModel createFileModelObject() throws FileNotFoundException {
        FileCategory fCategory = new FileCategory(CATEGORY_ID,CATEGORY_NAME);
        FileExtensionModel fExtension = new FileExtensionModel(FILE_EXTENSION_ID,FILE_EXTENSION);
        FileModel fModel = new FileModel();
        fModel.setCategory(fCategory);
        fModel.setExtension(fExtension);
        fModel.setFileId(FILE_ID);

        String filePath = "C:/Users/Tomas/Downloads/Tp04 Lenguajes Regulares Expresiones Regulares.pdf";
        File fileInFileSystem = new File(filePath);

        fModel.setName(fileInFileSystem.getName());

        ////////////
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[(int)fileInFileSystem.length()];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(fileInFileSystem);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        ///////////

        fModel.setSize(buffer.length);
        fModel.setFile(buffer);

        LocalDate currentTime = java.time.LocalDate.now();
        Date currentTimeDate = Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        fModel.setDate(currentTimeDate);

        return fModel;
    }

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "file_categories");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "file_extensions");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "files");
        jdbcTemplate.execute(String.format("INSERT INTO file_categories VALUES (%d, '%s')",CATEGORY_ID, CATEGORY_NAME));
        jdbcTemplate.execute(String.format("INSERT INTO file_extensions VALUES (%d, '%s')",FILE_EXTENSION_ID, FILE_EXTENSION));
    }

    @Test
    public void testCreate() throws FileNotFoundException {
        FileModel fileModel = fileDao.create(createFileModelObject());
        assertNotNull(fileModel);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "files"));
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testDeleteNoExist() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void getByIdNoExist() {
    }

    @Test
    public void testList() {
    }

    @Test
    public void testUpdate() {
    }

}
