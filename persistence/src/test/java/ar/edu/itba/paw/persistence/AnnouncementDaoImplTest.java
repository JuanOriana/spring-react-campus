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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
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
    private final int USER_ID = 1;
    private final int USER_FILE_NUMBER = 41205221;
    private final String USER_NAME = "Paw";
    private final String USER_SURNAME = "2021";
    private final String USER_USERNAME = "paw2021";
    private final String USER_EMAIL = "paw2021@itba.edu.ar";
    private final String USER_PASSWORD = "asd123";

    private final int COURSE_ID = 1;
    private final int COURSE_YEAR = 2021;
    private final int COURSE_QUARTER = 2;
    private final String COURSE_BOARD = "S1";

    private final int SUBJECT_ID = 1;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "Protos";

    private final Date date = new Date(8000);

    private static final RowMapper<Announcement> ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
            new Announcement(rs.getInt("announcementId"), rs.getDate("date"), rs.getString("title"),
                    rs.getString("content"), new User(rs.getInt("userId"), rs.getInt("fileNumber"),
                    rs.getString("name"), rs.getString("surname"), null, null,
                    null, rs.getBoolean("isAdmin")),null);

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "announcements");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
        final int YEAR = 2021;
        final int SUBJECT_ID = 1;
        String sqlInsertSubject = String.format("INSERT INTO subjects  VALUES (%d, 'subject_name', 'code')", SUBJECT_ID);
        String sqlInsertCourse = String.format("INSERT INTO courses  VALUES (%d, %d,1,'S1',%d)", COURSE_ID, SUBJECT_ID, YEAR);
        String sqlInsertUser = String.format("INSERT INTO users VALUES (%d, %d,'name','surname','username','email','password', %b)", 1, 1, true);
        jdbcTemplate.execute(sqlInsertUser);
        jdbcTemplate.execute(sqlInsertSubject);
        jdbcTemplate.execute(sqlInsertCourse);
    }

    private Announcement getMockAnnouncement() {
        Subject mockSubject = new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        Course mockCourse = new Course(COURSE_ID, COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, mockSubject);
        User mockUser = new User(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, true);
        return new Announcement(date, "title", "content", mockUser, mockCourse);
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
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        announcementDao.delete(id);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
    }

    @Test
    public void testDeleteNoExist() {
        final int id = 999;
        final int nonExistentId = 100;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        announcementDao.delete(nonExistentId);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
    }


    @Test
    public void getById() {
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        Optional<Announcement> announcementOptional = announcementDao.getById(id);
        assertTrue(announcementOptional.isPresent());
        assertEquals(id, announcementOptional.get().getAnnouncementId());
        assertEquals(COURSE_ID, announcementOptional.get().getCourse().getCourseId());
        assertEquals(USER_ID, announcementOptional.get().getAuthor().getUserId());
        assertEquals("test_title", announcementOptional.get().getTitle());
        assertEquals("test_content", announcementOptional.get().getContent());
    }

    @Test
    public void getByIdNoExist() {
        final int id = 999;
        final int nonExistentId = 100;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));

        Optional<Announcement> announcementOptional = announcementDao.getById(nonExistentId);

        assertFalse(announcementOptional.isPresent());
    }

    @Test
    public void testList() {
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        List<Announcement> list = announcementDao.list();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(id, list.get(0).getAnnouncementId());
    }

    @Test
    public void testListCourseAnnouncements() {
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        List<Announcement> list = announcementDao.listByCourse(COURSE_ID);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(id, list.get(0).getAnnouncementId());
    }

    @Test
    public void testListCourseAnnouncementsNonExistentId() {
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        List<Announcement> list = announcementDao.listByCourse(COURSE_ID + 1);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test

    public void testUpdate() {
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,userId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')", id, USER_ID, COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        Announcement announcement = getMockAnnouncement();
        announcement.setTitle("test_update_title");
        announcement.setContent("test_update_content");
        final boolean isUpdated = announcementDao.update(id, announcement);
        assertTrue(isUpdated);

        String sqlGetAnnouncementOfId = String.format("SELECT * FROM announcements NATURAL JOIN users WHERE announcementId = %d;", id);
        Announcement announcementDb = jdbcTemplate.query(sqlGetAnnouncementOfId,ANNOUNCEMENT_ROW_MAPPER).get(0);

        assertEquals(id, announcementDb.getAnnouncementId());
        assertEquals(USER_ID, announcementDb.getAuthor().getUserId());
        assertEquals("test_update_title", announcementDb.getTitle());
        assertEquals("test_update_content", announcementDb.getContent());
    }


}
