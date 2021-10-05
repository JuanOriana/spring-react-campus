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
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class FileDaoImplTest extends BasicPopulator {

    @Autowired
    private FileDao fileDao;

    private static final RowMapper<FileModel> FILE_MODEL_ROW_MAPPER = (rs, rowNum) ->
            new FileModel.Builder()
                    .withFileId(rs.getLong("fileId"))
                    .withSize(rs.getLong("fileSize"))
                    .withName(rs.getString("fileName"))
                    .withDate(rs.getTimestamp("fileDate").toLocalDateTime())
                    .withFile(rs.getBytes("file"))
                    .withDownloads(rs.getLong("downloads"))
                    .withExtension(new FileExtension(rs.getLong("fileExtensionId"), rs.getString("fileExtension")))
                    .withCourse(new Course.Builder()
                            .withCourseId(rs.getLong("courseId"))
                            .withYear(rs.getInt("year"))
                            .withQuarter(rs.getInt("quarter"))
                            .withBoard(rs.getString("board"))
                            .withSubject(new Subject(rs.getLong("subjectId"), rs.getString("code"),
                                    rs.getString("subjectName")))
                            .build())
                    .build();

    private FileCategory creatFileCategoryObject(Long fileCategoryId, String fileCategory) {
        return new FileCategory(fileCategoryId, fileCategory);
    }

    @Before
    public void setUp() {
        super.setUp();
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertUser(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_IS_ADMIN);
        insertRole(STUDENT_ROLE_ID, STUDENT_ROLE_NAME);
        insertUserToCourse(USER_ID, COURSE_ID, STUDENT_ROLE_ID);
        insertFileExtension(FILE_EXTENSION_ID, FILE_EXTENSION);
        insertFileExtension(FILE_EXTENSION_ID_OTHER, FILE_EXTENSION_OTHER);
        insertFileCategory(FILE_CATEGORY_ID, FILE_CATEGORY);
        insertFileCategory(FILE_CATEGORY_ID2, FILE_CATEGORY2);
    }

    @Test
    public void testCreate() {
        FileModel mockFile = createFileModelObject("src/test/resources/test.png", FILE_ID);
        FileModel fileModel = fileDao.create(mockFile.getSize(), LocalDateTime.now(), mockFile.getName(),
                mockFile.getFile(), mockFile.getCourse());
        assertNotNull(fileModel);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "files"));
    }

    @Test
    public void testDelete() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        fileDao.delete(FILE_ID);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "files"));
    }

    @Test(expected = AssertionError.class)
    public void testDeleteNoExist() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        final boolean isDeleted = fileDao.delete(FILE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
        assertFalse(isDeleted);
    }

    @Test
    public void testGetById() {
        FileModel fModel = createFileModelObject("src/test/resources/test.png", FILE_ID);
        insertFileModelToDB(fModel);

        Optional<FileModel> fileFromDB = fileDao.getById(FILE_ID);
        assertTrue(fileFromDB.isPresent());
        assertEquals(FILE_ID, fileFromDB.get().getFileId());
        assertEquals(FILE_EXTENSION_ID_OTHER, fileFromDB.get().getExtension().getFileExtensionId());
        assertEquals(COURSE_ID, fileFromDB.get().getCourse().getCourseId());
        assertEquals(fModel.getSize(), fileFromDB.get().getSize());
        assertEquals(FILE_NAME, fileFromDB.get().getName());
        assertEquals(fModel.getDate(), fileFromDB.get().getDate());
        assertArrayEquals(fModel.getFile(), fileFromDB.get().getFile());

    }

    @Test(expected = AssertionError.class)
    public void testGetByIdNoExist() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));

        Optional<FileModel> fileFromDB = fileDao.getById(FILE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
        assertFalse(fileFromDB.isPresent());
    }

    @Test
    public void testList() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        List<FileModel> list = fileDao.list(USER_ID);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testUpdate() {
        FileModel fModel = createFileModelObject("src/test/resources/test.png", FILE_ID);
        insertFileModelToDB(fModel);
        fModel.setName("nuevoNombre");
        final boolean isUpdated = fileDao.update(FILE_ID, fModel);
        assertTrue(isUpdated);

        FileModel fileDB = jdbcTemplate.query("SELECT * FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileId = ?", new Object[]{FILE_ID}, FILE_MODEL_ROW_MAPPER).get(0);

        assertEquals(FILE_ID, fileDB.getFileId());
        assertEquals(FILE_EXTENSION_ID_OTHER, fileDB.getExtension().getFileExtensionId());
        assertEquals("nuevoNombre", fileDB.getName());
    }

    @Test
    public void testAddCategory() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);

        boolean categoryAdded = fileDao.associateCategory(FILE_ID, fCategory.getCategoryId());
        assertTrue(categoryAdded);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));
    }

    @Test
    public void testGetFileCategories() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileCategory> list = fileDao.getFileCategories(FILE_ID);
        assertEquals(1, list.size());
    }

    @Test
    public void testGetByCategory() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.getByCategory(FILE_CATEGORY_ID);
        assertEquals(1, list.size());

        FileModel file = list.get(0);
        assertEquals(FILE_NAME, file.getName());

    }

    @Test
    public void testGetByCourseId() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));

        List<FileModel> list = fileDao.getByCourseId(COURSE_ID);
        assertEquals(1, list.size());

        FileModel file = list.get(0);
        assertEquals(COURSE_ID, file.getCourse().getCourseId());
    }


    @Test
    public void testListByCriteriaFilterByExtension() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));
        insertFileModelToDB(createFileModelObject("src/test/resources/test2.png", FILE_ID2));

        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        insertCategoryFileRelationShip(FILE_CATEGORY_ID2, FILE_ID2);

        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.listByCourse("",
                Collections.singletonList(FILE_EXTENSION_ID_OTHER), new ArrayList<>(), USER_ID, COURSE_ID,
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
    }

}
