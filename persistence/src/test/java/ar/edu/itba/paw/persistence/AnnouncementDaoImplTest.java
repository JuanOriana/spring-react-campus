package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Announcement;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class AnnouncementDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private AnnouncementDaoImpl announcementDao;

    private JdbcTemplate jdbcTemplate;
    private final int ANNOUNCEMENT_ID = 1;
    private final String ANNOUNCEMENT_CONTENT = "test_content";
    private final String ANNOUNCEMENT_TITLE = "test_title";

    private final int USER_ID = 1;
    private final int USER_FILE_NUMBER = 41205221;
    private final String USER_NAME = "Paw";
    private final String USER_SURNAME = "2021";
    private final String USER_USERNAME = "paw2021";
    private final String USER_EMAIL = "paw2021@itba.edu.ar";
    private final String USER_PASSWORD = "asd123";

    private final int COURSE_ID = 1;
    private final int COURSE_YEAR = 2021;
    private final int COURSE_QUARTER = 1;
    private final String COURSE_BOARD = "S1";

    private final int SUBJECT_ID = 1;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "Protos";

    private final long PAGE = 1;
    private final long PAGE_SIZE = 1;

    private final LocalDateTime ANNOUNCEMENT_DATE = LocalDateTime.now();

    private static final RowMapper<Announcement> ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
            new Announcement(rs.getInt("announcementId"), rs.getTimestamp("date").toLocalDateTime(), rs.getString("title"),
                    rs.getString("content"), new User(rs.getInt("userId"), rs.getInt("fileNumber"),
                    rs.getString("name"), rs.getString("surname"), null, null,
                    null, rs.getBoolean("isAdmin")),null);

    private void insertSubject(int subjectId, String subjectName, String code) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("subjects");
        Map<String, Object> args = new HashMap<>();
        args.put("subjectId", subjectId);
        args.put("subjectName", subjectName);
        args.put("code", code);
        subjectJdbcInsert.execute(args);
    }

    private void insertCourse(int courseId, int subjectId, int quarter, String board, int year) {
        SimpleJdbcInsert courseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
        Map<String, Object> args = new HashMap<>();
        args.put("courseId", courseId);
        args.put("subjectId", subjectId);
        args.put("quarter", quarter);
        args.put("board", board);
        args.put("year", year);
        courseJdbcInsert.execute(args);
    }

    private void insertUser(int userId, int fileNumber, String name, String surname, String username, String email,
                            String password, boolean isAdmin) {
        SimpleJdbcInsert userJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
        Map<String, Object> args = new HashMap<>();
        args.put("userId",userId);
        args.put("fileNumber",fileNumber);
        args.put("name",name);
        args.put("surname",surname);
        args.put("username",username);
        args.put("email",email);
        args.put("password",password);
        args.put("isAdmin",isAdmin);
        userJdbcInsert.execute(args);
    }

    private void insertAnnouncement(int announcementId, int userId, int courseId, String title, String content, LocalDateTime date) {
        SimpleJdbcInsert announcementJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcements");
        Map<String, Object> args = new HashMap<>();
        args.put("announcementId",announcementId);
        args.put("userId",userId);
        args.put("courseId",courseId);
        args.put("title",title);
        args.put("content",content);
        args.put("date", Timestamp.valueOf(date));
        announcementJdbcInsert.execute(args);
    }

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "announcements");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertUser(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, true);
    }

    private Announcement getMockAnnouncement() {
        Subject mockSubject = new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        Course mockCourse = new Course(COURSE_ID, COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, mockSubject);
        User mockUser = new User(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, true);
        return new Announcement(ANNOUNCEMENT_DATE, "test_title", "test_content", mockUser, mockCourse);
    }

    @Test
    public void testCreate() {
        final Announcement announcement = announcementDao.create(getMockAnnouncement());
        assertEquals( 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateNonExistentCourseId() {
        Announcement announcement = getMockAnnouncement();
        announcement.getCourse().setCourseId(COURSE_ID + 1);
        announcementDao.create(announcement);
        Assert.fail("Should have thrown assertion error  for non-existent foreign key 'course id' ");
    }

    @Test
    public void testDelete() {
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        announcementDao.delete(ANNOUNCEMENT_ID);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
    }

    @Test
    public void testDeleteNoExist() {
        final int NOT_EXISTING_ID = 100;
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        announcementDao.delete(NOT_EXISTING_ID);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
    }


    @Test
    public void getById() {
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        Optional<Announcement> announcementOptional = announcementDao.getById(ANNOUNCEMENT_ID);
        assertTrue(announcementOptional.isPresent());
        assertEquals(ANNOUNCEMENT_ID, announcementOptional.get().getAnnouncementId());
        assertEquals(COURSE_ID, announcementOptional.get().getCourse().getCourseId());
        assertEquals(USER_ID, announcementOptional.get().getAuthor().getUserId());
        assertEquals("test_title", announcementOptional.get().getTitle());
        assertEquals("test_content", announcementOptional.get().getContent());
    }

    @Test
    public void getByIdNoExist() {
        final int NOT_EXISTING_ID = 100;
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        Optional<Announcement> announcementOptional = announcementDao.getById(NOT_EXISTING_ID);
        assertFalse(announcementOptional.isPresent());
    }

    @Test
    public void testList() {
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        List<Announcement> list = announcementDao.list(PAGE, PAGE_SIZE);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(ANNOUNCEMENT_ID, list.get(0).getAnnouncementId());
    }

    @Test
    public void testListCourseAnnouncements() {
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        List<Announcement> list = announcementDao.listByCourse(COURSE_ID);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(ANNOUNCEMENT_ID, list.get(0).getAnnouncementId());
    }

    @Test
    public void testListCourseAnnouncementsNonExistentId() {
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        List<Announcement> list = announcementDao.listByCourse(COURSE_ID + 1);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test

    public void testUpdate() {
        insertAnnouncement(ANNOUNCEMENT_ID, USER_ID, COURSE_ID, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, ANNOUNCEMENT_DATE);
        Announcement announcement = getMockAnnouncement();
        announcement.setTitle("test_update_title");
        announcement.setContent("test_update_content");
        final boolean isUpdated = announcementDao.update(ANNOUNCEMENT_ID, announcement);
        assertTrue(isUpdated);

        String sqlGetAnnouncementOfId = String.format("SELECT * FROM announcements NATURAL JOIN users WHERE announcementId = %d;", ANNOUNCEMENT_ID);
        Announcement announcementDb = jdbcTemplate.query(sqlGetAnnouncementOfId,ANNOUNCEMENT_ROW_MAPPER).get(0);

        assertEquals(ANNOUNCEMENT_ID, announcementDb.getAnnouncementId());
        assertEquals(USER_ID, announcementDb.getAuthor().getUserId());
        assertEquals("test_update_title", announcementDb.getTitle());
        assertEquals("test_update_content", announcementDb.getContent());
    }


}
