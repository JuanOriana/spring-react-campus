package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.Subject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class BasicPopulator {

    @Autowired
    private DataSource ds;
    protected JdbcTemplate jdbcTemplate;

    protected final Long ANNOUNCEMENT_ID = 1L;
    protected final String ANNOUNCEMENT_CONTENT = "test_content";
    protected final String ANNOUNCEMENT_TITLE = "test_title";

    protected final Long USER_ID = 1L;
    protected final Integer USER_FILE_NUMBER = 41205221;
    protected final String USER_NAME = "Paw";
    protected final String USER_SURNAME = "2021";
    protected final String USER_USERNAME = "paw2021";
    protected final String USER_EMAIL = "paw2021@itba.edu.ar";
    protected final String USER_PASSWORD = "asd123";
    protected final boolean USER_IS_ADMIN = true;
    protected final Long USER_ID_INEXISTENCE = 9999L;
    protected final String USER_UPDATE_NAME = "Alan";

    protected final Long COURSE_ID = 1L;
    protected final Long INVALID_COURSE_ID = 9999L;
    protected final Integer COURSE_YEAR = 2021;
    protected final Integer COURSE_QUARTER = 1;
    protected final Integer UPDATE_QUARTER = 2;
    protected final String COURSE_BOARD = "S1";

    protected final Long SUBJECT_ID = 1L;
    protected final String SUBJECT_CODE = "A1";
    protected final String SUBJECT_NAME = "Protos";

    protected final String SORT_DIRECTION = "desc";
    protected final String SORT_PROPERTY = "date";

    protected final Integer PAGE = 1;
    protected final Integer PAGE_SIZE = 10;

    protected final Integer STUDENT_ROLE_ID = 1;
    protected final Integer TEACHER_ROLE_ID = 3;
    protected final String TEACHER_ROLE_NAME = "Teacher";
    protected final String STUDENT_ROLE_NAME = "Student";



    // FileExtension
    protected final Long FILE_EXTENSION_ID_OTHER = 0L;
    protected final String FILE_EXTENSION_OTHER = "other";
    protected final Long FILE_EXTENSION_ID = 1L;
    protected final String FILE_EXTENSION = "pdf";

    // FileCategory
    protected final Long FILE_CATEGORY_ID = 1L;
    protected final String FILE_CATEGORY = "Guia teorica";
    protected final Long FILE_CATEGORY_ID2 = 2L;
    protected final String FILE_CATEGORY2 = "PAW";
    protected final Long INEXISTENCE_FILE_CATEGORY_ID = 999L;

    // FileModel
    protected final Long FILE_ID = 1L;
    protected final Long FILE_ID2 = 2L;
    protected final String FILE_NAME2 = "test2.png";
    protected final String FILE_NAME = "test.png";



    protected final Integer TIME_TABLE_DAY_OF_WEEK = 1;
    protected final Time TIME_TABLE_START_OF_COURSE = new Time(TimeUnit.HOURS.toMillis(12));
    protected final Time TIME_TABLE_END_OF_COURSE = new Time(TimeUnit.HOURS.toMillis(14));



    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    protected void insertSubject(Long subjectId, String subjectName, String code) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("subjects");
        Map<String, Object> args = new HashMap<>();
        args.put("subjectId", subjectId);
        args.put("subjectName", subjectName);
        args.put("code", code);
        subjectJdbcInsert.execute(args);
    }

    protected void insertCourse(Long courseId, Long subjectId, int quarter, String board, int year) {
        SimpleJdbcInsert courseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
        Map<String, Object> args = new HashMap<>();
        args.put("courseId", courseId);
        args.put("subjectId", subjectId);
        args.put("quarter", quarter);
        args.put("board", board);
        args.put("year", year);
        courseJdbcInsert.execute(args);
    }

    protected void insertUser(Long userId, int fileNumber, String name, String surname, String username, String email,
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

    protected void insertAnnouncement(Long announcementId, Long userId, Long courseId, String title, String content, LocalDateTime date) {
        SimpleJdbcInsert announcementJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcements");
        Map<String, Object> args = new HashMap<>();
        args.put("announcementId", announcementId);
        args.put("userId", userId);
        args.put("courseId", courseId);
        args.put("title", title);
        args.put("content", content);
        args.put("date", Timestamp.valueOf(date));
        announcementJdbcInsert.execute(args);
    }

    protected void insertRole(int roleId, String roleName) {
        SimpleJdbcInsert roleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
        Map<String, Object> args = new HashMap<>();
        args.put("roleId", roleId);
        args.put("roleName", roleName);
        roleJdbcInsert.execute(args);
    }

    protected void insertUserToCourse(Long userId, Long courseId, int roleId) {
        SimpleJdbcInsert userToCourseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_to_course");
        Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("courseId", courseId);
        args.put("roleId", roleId);
        userToCourseJdbcInsert.execute(args);
    }
    protected void insertFileCategory(Long fileCategoryId, String fileCategoryName) {
        SimpleJdbcInsert fileCategoryJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_categories");
        Map<String, Object> args = new HashMap<>();

        args.put("categoryId", fileCategoryId);
        args.put("categoryName", fileCategoryName);

        fileCategoryJdbcInsert.execute(args);
    }

    protected void insertFileModelToDB(FileModel fModel) {
        SimpleJdbcInsert fileModelJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("files");
        Map<String, Object> args = new HashMap<>();
        args.put("fileExtensionId", fModel.getExtension().getFileExtensionId());
        args.put("fileSize", fModel.getSize());
        args.put("fileDate", fModel.getDate());
        args.put("fileName", fModel.getName());
        args.put("file", fModel.getFile());
        args.put("fileId", fModel.getFileId());
        args.put("courseId", fModel.getCourse().getCourseId());
        fileModelJdbcInsert.execute(args);
    }

    protected FileModel createFileModelObject(String filePath, long fileId) {
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


    protected void insertFileExtension(Long fileExtensionId, String fileExtension) {
        SimpleJdbcInsert fileExtensionsJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("file_extensions");

        Map<String, Object> args = new HashMap<>();
        args.put("fileExtensionId", fileExtensionId);
        args.put("fileExtension", fileExtension);

        fileExtensionsJdbcInsert.execute(args);
    }


    protected void insertRole(Integer roleId, String roleName) {
        SimpleJdbcInsert roleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
        Map<String, Object> args = new HashMap<>();
        args.put("roleId", roleId);
        args.put("roleName", roleName);
        roleJdbcInsert.execute(args);
    }

    protected void insertCategoryFileRelationShip(Long categoryId, Long fileId) {
        SimpleJdbcInsert categoryFileJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("category_file_relationship");

        Map<String, Object> args = new HashMap<>();
        args.put("categoryId", categoryId);
        args.put("fileId", fileId);

        categoryFileJdbcInsert.execute(args);
    }

    protected void insertTimeTable(Long courseId,Integer dayOfWeek,String start, String endTime){
        SimpleJdbcInsert timeTableJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("timetables");

        Map<String,Object>  args = new HashMap<>();
        args.put("courseId", courseId);
        args.put("dayOfWeek", dayOfWeek);
        args.put("startTime", start);
        args.put("endtime", endTime);
        timeTableJdbcInsert.execute(args);
    }


}
