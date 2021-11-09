package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.CampusPageRequest;
import ar.edu.itba.paw.models.CampusPageSort;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/file_populator.sql")
@Rollback
@Transactional
public class FileDaoImplTest extends BasicPopulator {

    private static final String SORT_DIRECTION = "ASC";
    private static final String SORT_PROPERTY = "NAME";
    @Autowired
    private FileDao fileDao;

    private final Long DB_FILE_ID = 1337L;
    private final Long DB_USER_ID = 1337L;


    @Test
    public void testCreate() {
        FileModel mockFile = createFileModelObject(FILE_PATH, FILE_ID);
        FileModel fileModel = fileDao.create(mockFile.getSize(), LocalDateTime.now(), mockFile.getName(),
                mockFile.getFile(), mockFile.getCourse());
        assertNotNull(fileModel);
    }

    @Test
    public void testUpdate() {
        FileModel fModel = createFileModelObject("src/test/resources/test.png", 1337);
        fModel.setName("nuevoNombre");
        assertTrue(fileDao.update(DB_FILE_ID, fModel));
    }

    @Test
    public void testDelete() {
        assertTrue(fileDao.delete(DB_FILE_ID));
    }

    @Test(expected = AssertionError.class)
    public void testDeleteNoExist() {
        fileDao.delete(9999L);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
    }

    @Test
    public void testFindById() {
        Optional<FileModel> fileFromDB = fileDao.findById(DB_FILE_ID);
        assertTrue(fileFromDB.isPresent());
        assertEquals(DB_FILE_ID, fileFromDB.get().getFileId());
    }

    @Test(expected = AssertionError.class)
    public void testFindByIdNoExist() {
        fileDao.findById(FILE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
    }

    @Test
    public void testList() {
        List<FileModel> list = fileDao.list(DB_FILE_ID);
        assertNotNull(list);
        assertEquals(2L, list.size());
    }

    @Test
    public void testGetFileCategories() {
        List<FileCategory> list = fileDao.getFileCategories(DB_FILE_ID);
        assertEquals(1, list.size());
    }

    @Test
    public void testFindByCategory() {
        List<FileModel> list = fileDao.findByCategory(2L);

        assertEquals(2L, list.size());
        assertEquals(FILE_NAME, list.get(0).getName());

    }

    @Test
    public void testFindByCourseId() {
        List<FileModel> list = fileDao.findByCourseId(COURSE_ID);
        assertEquals(2L, list.size());
        FileModel file = list.get(0);
        assertEquals(COURSE_ID, file.getCourse().getCourseId());
    }


    @Test
    public void testListByCourseCriteriaFilterByExtension() {
        List<FileModel> list = fileDao.listByCourse("",
                Collections.singletonList(1L), new ArrayList<>(), DB_USER_ID, COURSE_ID,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(2, list.size());
    }

    @Test
    public void testListByCourseCriteriaFilterByExtensionInexistence() {
        List<FileModel> list = fileDao.listByCourse("",
                Collections.singletonList(999L), new ArrayList<>(), DB_USER_ID, COURSE_ID,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(0, list.size());
    }

    @Test
    public void testListByCourseCriteriaFilterByCategory() {
        List<FileModel> list = fileDao.listByCourse("",
                new ArrayList<>(), Collections.singletonList(2L), DB_USER_ID, COURSE_ID,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(2, list.size());
    }

    @Test
    public void testListByUserCriteriaFilterByCategory() {
        List<FileModel> list = fileDao.listByUser("",
                new ArrayList<>(), Collections.singletonList(2L), DB_USER_ID,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(2, list.size());
    }

    @Test
    public void testHasAccess() {
        assertTrue(fileDao.hasAccess(DB_FILE_ID, DB_USER_ID));
    }


/*
    @Test
    public void testListByCriteriaEmptyCourse() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.listByCourse("", new ArrayList<>(), new ArrayList<>(), USER_ID, 999L,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testListByCriteriaDateAndCategory() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.listByCourse("", new ArrayList<>(),
                Collections.singletonList(FILE_CATEGORY_ID), USER_ID, COURSE_ID,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(1, list.size());
    }

    @Test
    public void testListByCriteriaSearch1File() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertFileModelToDB(createFileModelObject("src/test/resources/test2.png", FILE_ID2));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        insertCategoryFileRelationShip(FILE_CATEGORY_ID2, FILE_ID2);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.listByCourse(FILE_NAME2, new ArrayList<>(), new ArrayList<>(),
                USER_ID, COURSE_ID, new CampusPageRequest(PAGE, PAGE_SIZE),
                new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(1, list.size());
        assertEquals(FILE_ID2, list.get(0).getFileId());
    }

}*/

}
