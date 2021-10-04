package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
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
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class FileDaoImplTest {


    @Autowired
    private DataSource ds;

    @Autowired
    private FileDao fileDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert JdbcInsert;

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

    // Course & Subject
    private final Long COURSE_ID = 1L;
    private final Integer ROLE_ID = 1;
    private final String ROLE_NAME = "Teacher";
    private final Integer COURSE_YEAR = 2021;
    private final Integer COURSE_QUARTER = 1;
    private final String COURSE_BOARD = "S1";

    private final Long SUBJECT_ID = 1L;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "Protos";

    // FileExtension
    private final Long FILE_EXTENSION_ID_OTHER = 0L;
    private final String FILE_EXTENSION_OTHER = "other";
    private final Long FILE_EXTENSION_ID = 1L;
    private final String FILE_EXTENSION = "pdf";

    // FileCategory
    private final Long FILE_CATEGORY_ID = 1L;
    private final String FILE_CATEGORY = "TLA";
    private final Long FILE_CATEGORY_ID2 = 2L;
    private final String FILE_CATEGORY2 = "PAW";
    private final Long INEXISTENCE_FILE_CATEGORY_ID = 999L;

    // FileModel
    private final Long FILE_ID = 1L;
    private final Long FILE_ID2 = 2L;
    private final String FILE_NAME2 = "test2.png";
    private final String FILE_NAME = "test.png";

    //User
    private final Long USER_ID = 1L;
    private final Integer FILE_NUMBER = 1;
    private final String NAME = "John";
    private final String SURNAME = "Doe";
    private final String USERNAME = "johndoe";
    private final String EMAIL = "johndoe@lorem.com";
    private final String PASSWORD = "d8d3aedd4b5d0ce0131600eaadc48dcb";
    private final boolean IS_ADMIN = true;

    private final String SORT_DIRECTION = "desc";
    private final String SORT_PROPERTY = "date";
    private final Integer PAGE = 1;
    private final Integer PAGE_SIZE = 10;


    private FileModel createFileModelObject(String filePath, long fileId) {
        FileExtension fExtension = new FileExtension(FILE_EXTENSION_ID_OTHER, FILE_EXTENSION_OTHER);
        Course course = new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(COURSE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
        File fileInFileSystem = new File(filePath);
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[(int) fileInFileSystem.length()];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(fileInFileSystem);
            int read;
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
                e.printStackTrace();
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new FileModel.Builder()
                .withCourse(course)
                .withExtension(fExtension)
                .withFileId(fileId)
                .withName(fileInFileSystem.getName())
                .withSize((long) buffer.length)
                .withFile(buffer)
                .withDate(LocalDateTime.now())
                .withDownloads(0L)
                .build();
    }

    private void insertFileModelToDB(FileModel fModel) {
        Map<String, Object> args = new HashMap<>();
        args.put("fileExtensionId", fModel.getExtension().getFileExtensionId());
        args.put("fileSize", fModel.getSize());
        args.put("fileDate", fModel.getDate());
        args.put("fileName", fModel.getName());
        args.put("file", fModel.getFile());
        args.put("fileId", fModel.getFileId());
        args.put("courseId", fModel.getCourse().getCourseId());
        JdbcInsert.execute(args);
    }

    private void insertSubject(Long subjectId, String subjectName, String code) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("subjects");
        Map<String, Object> args = new HashMap<>();
        args.put("subjectId", subjectId);
        args.put("subjectName", subjectName);
        args.put("code", code);
        subjectJdbcInsert.execute(args);
    }

    private void insertCourse(Long courseId, Long subjectId, int quarter, String board, int year) {
        SimpleJdbcInsert courseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
        Map<String, Object> args = new HashMap<>();
        args.put("courseId", courseId);
        args.put("subjectId", subjectId);
        args.put("quarter", quarter);
        args.put("board", board);
        args.put("year", year);
        courseJdbcInsert.execute(args);
    }


    private void insertUser(Long userId, int fileNumber, String name, String surname, String username, String email,
                            String password, boolean isAdmin) {
        SimpleJdbcInsert userJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
        SimpleJdbcInsert profileImageJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("profile_images");
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("fileNumber", fileNumber);
        args.put("name", name);
        args.put("surname", surname);
        args.put("username", username);
        args.put("email", email);
        args.put("password", password);
        args.put("isAdmin", isAdmin);
        userJdbcInsert.execute(args);
        Map<String, Object> argsProfileImage = new HashMap<>();
        argsProfileImage.put("image", null);
        argsProfileImage.put("userid", userId);
        profileImageJdbcInsert.execute(argsProfileImage);
    }

    private void insertRole(Integer roleId, String roleName) {
        SimpleJdbcInsert roleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
        Map<String, Object> args = new HashMap<>();
        args.put("roleId", roleId);
        args.put("roleName", roleName);
        roleJdbcInsert.execute(args);
    }

    private void insertUserToCourse(Long userId, Long courseId, int roleId) {
        SimpleJdbcInsert userToCourseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_to_course");
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("courseId", courseId);
        args.put("roleId", roleId);
        userToCourseJdbcInsert.execute(args);
    }

    private void insertFileExtension(Long fileExtensionId, String fileExtension) {
        SimpleJdbcInsert fileExtensionsJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_extensions");

        Map<String, Object> args = new HashMap<>();
        args.put("fileExtensionId", fileExtensionId);
        args.put("fileExtension", fileExtension);

        fileExtensionsJdbcInsert.execute(args);
    }

    private void insertFileCategory(Long fileCategoryId, String fileCategoryName) {
        SimpleJdbcInsert fileCategoryJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_categories");
        Map<String, Object> args = new HashMap<>();

        args.put("categoryId", fileCategoryId);
        args.put("categoryName", fileCategoryName);

        fileCategoryJdbcInsert.execute(args);
    }


    private FileCategory creatFileCategoryObject(Long fileCategoryId, String fileCategory) {
        return new FileCategory(fileCategoryId, fileCategory);
    }

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files");
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertUser(USER_ID, FILE_NUMBER, NAME, SURNAME, USERNAME, EMAIL, PASSWORD, IS_ADMIN);
        insertRole(ROLE_ID, ROLE_NAME);
        insertUserToCourse(USER_ID, COURSE_ID, ROLE_ID);
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
    public void testGetByName() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));

        List<FileModel> list = fileDao.getByName(FILE_NAME);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testGetByExtension() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));

        List<FileModel> list = fileDao.getByExtension(FILE_EXTENSION_ID_OTHER);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testGetByExtensionName() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));

        List<FileModel> list = fileDao.getByExtension(FILE_EXTENSION_OTHER);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
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
    public void testRemoveCategory() {
        insertFileModelToDB(createFileModelObject("src/test/resources/test.png", FILE_ID));

        insertCategoryFileRelationShip(FILE_CATEGORY_ID, FILE_ID);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        boolean categoryRemoved = fileDao.removeCategory(FILE_ID, FILE_CATEGORY_ID);
        assertTrue(categoryRemoved);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));
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

    private void insertCategoryFileRelationShip(Long categoryId, Long fileId) {
        SimpleJdbcInsert categoryFileJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("category_file_relationship");

        Map<String, Object> args = new HashMap<>();
        args.put("categoryId", categoryId);
        args.put("fileId", fileId);

        categoryFileJdbcInsert.execute(args);
    }

}
