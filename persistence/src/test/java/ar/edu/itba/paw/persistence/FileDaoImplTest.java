package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
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

    @Autowired
    private FileDao fileDao;


    private FileCategory creatFileCategoryObject(Long fileCategoryId, String fileCategory) {
        return new FileCategory(fileCategoryId, fileCategory);
    }

    @Test
    public void testCreate() {
        FileModel mockFile = createFileModelObject("src/test/resources/test.png", FILE_ID);
        FileModel fileModel = fileDao.create(mockFile.getSize(), LocalDateTime.now(), mockFile.getName(),
                mockFile.getFile(), mockFile.getCourse());
        assertNotNull(fileModel);
    }

    @Test
    public void testDelete() {
        assertTrue(fileDao.delete(1337L));
    }

    @Test(expected = AssertionError.class)
    public void testDeleteNoExist() {
        fileDao.delete(9999L);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
    }

    @Test
    public void testGetById() {
        Optional<FileModel> fileFromDB = fileDao.findById(1337L);
        assertTrue(fileFromDB.isPresent());
        assertEquals(1337L, fileFromDB.get().getFileId().longValue());
    }

    @Test(expected = AssertionError.class)
    public void testGetByIdNoExist() {
        fileDao.findById(FILE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
    }

    @Test
    public void testList() {
        List<FileModel> list = fileDao.list(1337L);
        assertNotNull(list);
        assertEquals(2L, list.size());
    }

    @Test
    public void testUpdate() {
        FileModel fModel = createFileModelObject("src/test/resources/test.png", 1337);
        fModel.setName("nuevoNombre");
        assertTrue(fileDao.update(1337L, fModel));
    }

    @Test
    public void testGetFileCategories() {
        List<FileCategory> list = fileDao.getFileCategories(1337L);
        assertEquals(1, list.size());
    }

    @Test
    public void testGetByCategory() {
        List<FileModel> list = fileDao.findByCategory(2L);
        assertEquals(2L, list.size());

        FileModel file = list.get(0);
        assertEquals(FILE_NAME, file.getName());

    }

    @Test
    public void testGetByCourseId() {
        List<FileModel> list = fileDao.findByCourseId(2L);
        assertEquals(2L, list.size());
        FileModel file = list.get(0);
        assertEquals(2L, file.getCourse().getCourseId().longValue());
    }

 /*
    @Test
    public void testListByCriteriaFilterByExtension() {
        List<FileModel> list = fileDao.listByCourse("",
                Collections.singletonList(1L), new ArrayList<>(), 1337L, 2L,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(2, list.size());
    }

   @Test
    public void testListByCriteriaFilterByCategory() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertFileModelToDB(createFileModelObject("src/test/resources/test2.png", FILE_ID2));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        insertCategoryFileRelationShip(FILE_CATEGORY_ID2, FILE_ID2);
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.listByCourse("",
                new ArrayList<>(), Collections.singletonList(FILE_CATEGORY_ID), USER_ID, COURSE_ID,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testListByCriteriaFilterByCategoryInexistance() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.listByCourse("",
                new ArrayList<>(), Collections.singletonList(INEXISTENCE_FILE_CATEGORY_ID), USER_ID, COURSE_ID,
                new CampusPageRequest(PAGE, PAGE_SIZE), new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertTrue(list.isEmpty());
    }

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

    @Test
    public void testListByCriteriaSearch2Files() { // If name of file 1 or 2 is changed this test might fail
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertFileModelToDB(createFileModelObject("src/test/resources/test2.png", FILE_ID2));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        insertCategoryFileRelationShip(FILE_CATEGORY_ID2, FILE_ID2);

        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));
        List<FileModel> list = fileDao.listByCourse("test", new ArrayList<>(),
                new ArrayList<>(), USER_ID, COURSE_ID, new CampusPageRequest(PAGE, PAGE_SIZE),
                new CampusPageSort(SORT_DIRECTION, SORT_PROPERTY)).getContent();
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }*/

}
