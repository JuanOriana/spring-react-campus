package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileExtensionDao;
import ar.edu.itba.paw.models.FileExtension;
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
@Sql("classpath:populators/file_extension_populator.sql")
@Rollback
@Transactional
public class FileExtensionDaoImplTest {
    private static final Long FILE_EXTENSION_ID = 1L;
    private static final String FILE_EXTENSION = "pdf";

    @Autowired
    private FileExtensionDao fileExtensionDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testCreate() {
        FileExtension fileExtension = fileExtensionDao.create(FILE_EXTENSION);
        assertNotNull(fileExtension);
        assertEquals(FILE_EXTENSION, fileExtension.getFileExtensionName());
    }

    @Test
    public void testUpdate() {
        String newExtension = "doc";
        boolean wasUpdated = fileExtensionDao.update(1337, newExtension);
        assertTrue(wasUpdated);
    }

    @Test
    public void testDelete() {
        boolean wasDeleted = fileExtensionDao.delete(1337);
        assertTrue(wasDeleted);
    }

    @Test
    public void testGetCategories() {
        List<FileExtension> list = fileExtensionDao.getExtensions();
        assertEquals(1, list.size());
        FileExtension fileExtension = list.get(0);
        assertEquals(1337, fileExtension.getFileExtensionId().intValue());

    }
}
