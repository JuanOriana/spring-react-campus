package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Announcement;
import org.junit.Assert;
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
import javax.swing.text.html.Option;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final int TEACHER_ID = 1;
    private final int COURSE_ID = 1;
    private final int YEAR = 2021;
    private final Date d = new Date();
    private final DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "announcements");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teachers");
        String sqlInsertCourse = String.format("INSERT INTO courses  VALUES (%d,'test_name','test_code',1,'test_board',%d)",COURSE_ID,YEAR);
        String sqlInsertTeacher = String.format("INSERT INTO teachers VALUES (1,'test_name','test_surname','test_email','test_username','test_password')",TEACHER_ID);
        jdbcTemplate.execute(sqlInsertCourse);
        jdbcTemplate.execute(sqlInsertTeacher);
    }

    @Test
    public void testCreate() {
        final boolean isCreated = announcementDao.create(new Announcement(TEACHER_ID, COURSE_ID, d, "test_title", "test_content"));

        assertEquals(true, isCreated);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateInexistenceTeacherId() {
        final boolean isCreated = announcementDao.create(new Announcement(TEACHER_ID+1, COURSE_ID, d, "test_title", "test_content"));

        Assert.fail("Should have thrown runtime exception for inexistence foreing key 'teacher id' ");
    }

    @Test(expected = RuntimeException.class)
    public void testCreateInexistenceCourseId() {
        final boolean isCreated = announcementDao.create(new Announcement(TEACHER_ID, COURSE_ID+1, d, "test_title", "test_content"));
        Assert.fail("Should have thrown runtime exception for inexistence foreing key 'course id' ");
    }

    @Test
    public void testDelete(){
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,teacherId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')",id, TEACHER_ID,COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        announcementDao.delete(id);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
    }

    @Test
    public void testDeleteNoExist(){
        final int id = 999;
        final int inexistenceId = 100;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,teacherId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')",id, TEACHER_ID,COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
        announcementDao.delete(inexistenceId);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));
    }


    @Test
    public void getById(){
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,teacherId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')",id, TEACHER_ID,COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));

        Optional<Announcement> announcementOptional = announcementDao.getById(id);

        assertTrue(announcementOptional.isPresent());
        assertEquals(id, announcementOptional.get().getAnnouncementId());
        assertEquals(COURSE_ID, announcementOptional.get().getcourseId());
        assertEquals(TEACHER_ID, announcementOptional.get().getTeacherId());
        assertEquals("test_title", announcementOptional.get().getTitle());
        assertEquals("test_content",announcementOptional.get().getContent());
    }

    @Test
    public void getByIdNoExist(){
        final int id = 999;
        final int inexistenceId = 100;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,teacherId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')",id, TEACHER_ID,COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "announcements"));

        Optional<Announcement> announcementOptional = announcementDao.getById(inexistenceId);

        assertFalse(announcementOptional.isPresent());
    }

    @Test
    public void testList(){
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,teacherId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')",id, TEACHER_ID,COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        List<Announcement> list = announcementDao.list();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(id,list.get(0).getAnnouncementId());
    }

    @Test
    public void testListCourseAnnouncements(){
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,teacherId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')",id, TEACHER_ID,COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        List<Announcement> list = announcementDao.listByCourse(COURSE_ID);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(id,list.get(0).getAnnouncementId());
    }

    @Test
    public void testListCourseAnnouncementsInexistenceId(){
        final int id = 999;
        String sqlInsertAnnouncement = String.format("INSERT INTO announcements (announcementId,teacherId, courseId, title,content) VALUES (%d,%d,%d,'test_title','test_content')",id, TEACHER_ID,COURSE_ID);
        jdbcTemplate.execute(sqlInsertAnnouncement);
        List<Announcement> list = announcementDao.listByCourse(COURSE_ID+1);
        assertNotNull(list);
        assertEquals(0, list.size());
    }



}
