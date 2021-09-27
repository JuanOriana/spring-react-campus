package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
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
    private final Long ROLE_ID = 1L;
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

    private final Long USER_ID = 1L;
    private final Integer FILE_NUMBER = 1;
    private final String NAME = "John";
    private final String SURNAME = "Doe";
    private final String USERNAME = "johndoe";
    private final String EMAIL = "johndoe@lorem.com";
    private final String PASSWORD = "d8d3aedd4b5d0ce0131600eaadc48dcb";
    private final boolean IS_ADMIN = true;


    private FileModel createFileModelObject() throws FileNotFoundException {
        FileExtension fExtension = new FileExtension(FILE_EXTENSION_ID_OTHER, FILE_EXTENSION_OTHER);
        Course course = new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(COURSE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
        String filePath = "src/test/resources/test.png";
        File fileInFileSystem = new File(filePath);
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[(int) fileInFileSystem.length()];
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
        return new FileModel.Builder()
                .withCourse(course)
                .withExtension(fExtension)
                .withFileId(FILE_ID)
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
        args.put("courseId", COURSE_ID);
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

    private FileCategory creatFileCategoryObject(Long fileCategoryId, String fileCategory) {
        return new FileCategory(fileCategoryId, fileCategory);
    }

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "category_file_relationship");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "files");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "file_categories");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "file_extensions");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_to_course");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "roles");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        jdbcTemplate.execute(String.format("INSERT INTO users (userId,fileNumber,name,surname,username,email,password,isAdmin) VALUES (%d,%d,'%s','%s','%s','%s','%s',%s)", USER_ID, FILE_NUMBER, NAME, SURNAME, USERNAME, EMAIL, PASSWORD, IS_ADMIN));
        jdbcTemplate.execute(String.format("INSERT INTO roles VALUES (%d, 'Teacher')", ROLE_ID));
        jdbcTemplate.execute(String.format("INSERT INTO user_to_course VALUES (%d, %d, %d)", USER_ID, COURSE_ID, ROLE_ID));
        jdbcTemplate.execute(String.format("INSERT INTO file_extensions VALUES (%d, '%s')", FILE_EXTENSION_ID_OTHER, FILE_EXTENSION_OTHER));
        jdbcTemplate.execute(String.format("INSERT INTO file_extensions VALUES (%d, '%s')", FILE_EXTENSION_ID, FILE_EXTENSION));
        jdbcTemplate.execute(String.format("INSERT INTO file_categories VALUES (%d, '%s')", FILE_CATEGORY_ID, FILE_CATEGORY));
        jdbcTemplate.execute(String.format("INSERT INTO file_categories VALUES (%d, '%s')", FILE_CATEGORY_ID2, FILE_CATEGORY2));
    }

    @Test
    public void testCreate() throws FileNotFoundException {
        FileModel mockFile = createFileModelObject();
        FileModel fileModel = fileDao.create(mockFile.getSize(), LocalDateTime.now(), mockFile.getName(),
                mockFile.getFile(), mockFile.getCourse());
        assertNotNull(fileModel);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "files"));
    }

    @Test
    public void testDelete() throws FileNotFoundException {
        insertFileModelToDB(createFileModelObject());
        fileDao.delete(FILE_ID);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "files"));
    }

    @Test(expected = AssertionError.class)
    public void testDeleteNoExist() throws FileNotFoundException {
        insertFileModelToDB(createFileModelObject());
        final boolean isDeleted = fileDao.delete(FILE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
        assertFalse(isDeleted);
    }

    @Test
    public void testGetById() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);

        Optional<FileModel> fileFromDB = fileDao.getById(FILE_ID);
        assertTrue(fileFromDB.isPresent());
        assertEquals(FILE_ID, fileFromDB.get().getFileId());
        assertEquals(FILE_EXTENSION_ID_OTHER, fileFromDB.get().getExtension().getFileExtensionId());
        assertEquals(COURSE_ID, fileFromDB.get().getCourse().getCourseId());
        assertEquals(fModel.getSize(), fileFromDB.get().getSize());
        assertEquals(fModel.getName(), fileFromDB.get().getName());
        assertEquals(fModel.getDate(), fileFromDB.get().getDate());
        assertArrayEquals(fModel.getFile(), fileFromDB.get().getFile());

    }

    @Test(expected = AssertionError.class)
    public void testGetByIdNoExist() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);

        Optional<FileModel> fileFromDB = fileDao.getById(FILE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent key 'file id' ");
        assertFalse(fileFromDB.isPresent());
    }

    @Test
    public void testList() throws FileNotFoundException {
        insertFileModelToDB(createFileModelObject());
        List<FileModel> list = fileDao.list(USER_ID);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testUpdate() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);
        fModel.setName("nuevoNombre");
        final boolean isUpdated = fileDao.update(FILE_ID, fModel);
        assertTrue(isUpdated);

        String sqlGetFileOfId = String.format("SELECT * FROM files NATURAL JOIN file_extensions NATURAL JOIN courses NATURAL JOIN subjects WHERE fileId = %d;", FILE_ID);
        FileModel fileDB = jdbcTemplate.query(sqlGetFileOfId, FILE_MODEL_ROW_MAPPER).get(0);

        assertEquals(FILE_ID, fileDB.getFileId());
        assertEquals(FILE_EXTENSION_ID_OTHER, fileDB.getExtension().getFileExtensionId());
        assertEquals("nuevoNombre", fileDB.getName());
    }

    @Test
    public void testGetByName() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);

        List<FileModel> list = fileDao.getByName(FILE_NAME);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testGetByExtension() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);

        List<FileModel> list = fileDao.getByExtension(FILE_EXTENSION_ID_OTHER);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testGetByExtensionName() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);

        List<FileModel> list = fileDao.getByExtension(FILE_EXTENSION_OTHER);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testAddCategory() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);

        boolean categoryAdded = fileDao.addCategory(fModel.getFileId(), fCategory.getCategoryId());
        assertTrue(categoryAdded);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));
    }

    @Test
    public void testRemoveCategory() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);

        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fModel.getFileId()));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        boolean categoryRemoved = fileDao.removeCategory(fModel.getFileId(), fCategory.getCategoryId());
        assertTrue(categoryRemoved);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));
    }

    @Test
    public void testGetFileCategories() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fModel.getFileId()));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileCategory> list = fileDao.getFileCategories(fModel.getFileId());
        assertEquals(1, list.size());
    }

    @Test
    public void testGetByCategory() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fModel.getFileId()));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.getByCategory(fCategory.getCategoryId());
        assertEquals(1, list.size());

        FileModel file = list.get(0);
        assertEquals(fModel.getName(), file.getName());

    }

    @Test
    public void testGetByCourseId() throws FileNotFoundException {
        FileModel fModel = createFileModelObject();
        insertFileModelToDB(fModel);

        List<FileModel> list = fileDao.getByCourseId(fModel.getCourse().getCourseId());
        assertEquals(1, list.size());

        FileModel file = list.get(0);
        assertEquals(fModel.getCourse().getCourseId(), file.getCourse().getCourseId());
    }

    private FileModel createFileModelObject2() throws FileNotFoundException {
        FileExtension fExtension = new FileExtension(FILE_EXTENSION_ID_OTHER, FILE_EXTENSION_OTHER);
        Course course = new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(COURSE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
        String filePath = "src/test/resources/test2.png";
        File fileInFileSystem = new File(filePath);
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[(int) fileInFileSystem.length()];
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
        return new FileModel.Builder()
                .withCourse(course)
                .withExtension(fExtension)
                .withFileId(FILE_ID2)
                .withName(fileInFileSystem.getName())
                .withSize((long) buffer.length)
                .withFile(buffer)
                .withDownloads(0L)
                .withDate(LocalDateTime.now())
                .build();
    }

    @Test
    public void testListByCriteriaFilterByExtension() throws FileNotFoundException {
        FileModel fileModel = createFileModelObject();
        insertFileModelToDB(fileModel);
        FileModel fileModel2 = createFileModelObject2();
        insertFileModelToDB(fileModel2);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        FileCategory fCategory2 = creatFileCategoryObject(FILE_CATEGORY_ID2, FILE_CATEGORY2);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fileModel.getFileId()));
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory2.getCategoryId(), fileModel2.getFileId()));
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.findFileByPage("",
                Collections.singletonList(FILE_EXTENSION_ID_OTHER), new ArrayList<>(), USER_ID, COURSE_ID, PageRequest.of(0,10)).toList();
        assertEquals(2, list.size());
    }

    @Test
    public void testListByCriteriaFilterByCategory() throws FileNotFoundException {
        FileModel fileModel = createFileModelObject();
        insertFileModelToDB(fileModel);
        FileModel fileModel2 = createFileModelObject2();
        insertFileModelToDB(fileModel2);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        FileCategory fCategory2 = creatFileCategoryObject(FILE_CATEGORY_ID2, FILE_CATEGORY2);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fileModel.getFileId()));
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory2.getCategoryId(), fileModel2.getFileId()));
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.findFileByPage("", new ArrayList<>(),
                Collections.singletonList(FILE_CATEGORY_ID), USER_ID, COURSE_ID, PageRequest.of(0,10)).toList();
        assertEquals(1, list.size());
        assertEquals(FILE_ID, list.get(0).getFileId());
    }

    @Test
    public void testListByCriteriaFilterByCategoryInexistance() throws FileNotFoundException {
        FileModel fileModel = createFileModelObject();
        insertFileModelToDB(fileModel);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fileModel.getFileId()));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.findFileByPage("", new ArrayList<>(),
                Collections.singletonList(INEXISTENCE_FILE_CATEGORY_ID), USER_ID, COURSE_ID, PageRequest.of(0,10)).toList();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testListByCriteriaEmptyCouse() throws FileNotFoundException {
        FileModel fileModel = createFileModelObject();
        insertFileModelToDB(fileModel);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fileModel.getFileId()));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.findFileByPage("", new ArrayList<>(), new ArrayList<>(),
                USER_ID, 999L, PageRequest.of(0,10)).toList();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testListByCriteriaDateAndCategory() throws FileNotFoundException {
        FileModel fileModel = createFileModelObject();
        insertFileModelToDB(fileModel);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fileModel.getFileId()));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.findFileByPage("", new ArrayList<>(),
                new ArrayList<>(Collections.singleton(FILE_CATEGORY_ID)), USER_ID, COURSE_ID,PageRequest.of(0,10)).toList();
        assertEquals(1, list.size());
    }

    @Test
    public void testListByCriteriaSearch1File() throws FileNotFoundException {
        FileModel fileModel = createFileModelObject();
        insertFileModelToDB(fileModel);
        FileModel fileModel2 = createFileModelObject2();
        insertFileModelToDB(fileModel2);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        FileCategory fCategory2 = creatFileCategoryObject(FILE_CATEGORY_ID2, FILE_CATEGORY2);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fileModel.getFileId()));
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory2.getCategoryId(), fileModel2.getFileId()));
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));

        List<FileModel> list = fileDao.findFileByPage(FILE_NAME2, new ArrayList<>(), new ArrayList<>(),
                USER_ID, COURSE_ID, PageRequest.of(0,10)).toList();
        assertEquals(1, list.size());
        assertEquals(FILE_ID2, list.get(0).getFileId());
    }

    @Test
    public void testListByCriteriaSearch2Files() throws FileNotFoundException { // If name of file 1 or 2 is changed this test might fail
        FileModel fileModel = createFileModelObject();
        insertFileModelToDB(fileModel);
        FileModel fileModel2 = createFileModelObject2();
        insertFileModelToDB(fileModel2);
        FileCategory fCategory = creatFileCategoryObject(FILE_CATEGORY_ID, FILE_CATEGORY);
        FileCategory fCategory2 = creatFileCategoryObject(FILE_CATEGORY_ID2, FILE_CATEGORY2);
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory.getCategoryId(), fileModel.getFileId()));
        jdbcTemplate.execute(String.format("INSERT INTO category_file_relationship VALUES (%d, %d);", fCategory2.getCategoryId(), fileModel2.getFileId()));
        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "category_file_relationship"));
        List<FileModel> list = fileDao.findFileByPage("test", new ArrayList<>(),
                new ArrayList<>(), USER_ID, COURSE_ID, PageRequest.of(0,10)).toList();
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

}
