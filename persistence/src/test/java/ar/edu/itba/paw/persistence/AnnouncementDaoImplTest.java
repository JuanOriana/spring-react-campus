package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/announcement_populator.sql")
@Rollback
@Transactional
public class AnnouncementDaoImplTest extends BasicPopulator {

    @Autowired
    private AnnouncementDao announcementDao;


    private final LocalDateTime ANNOUNCEMENT_DATE = LocalDateTime.now();

    private static final RowMapper<Announcement> ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
            new Announcement.Builder()
                    .withAnnouncementId(rs.getLong("announcementid"))
                    .withDate(rs.getTimestamp("date").toLocalDateTime())
                    .withTitle(rs.getString("title"))
                    .withContent(rs.getString("content"))
                    .withAuthor(new User.Builder()
                            .withUserId(rs.getLong("userId"))
                            .withFileNumber(rs.getInt("fileNumber"))
                            .withName(rs.getString("name"))
                            .withSurname(rs.getString("surname"))
                            .withUsername(rs.getString("username"))
                            .withEmail(rs.getString("email"))
                            .withPassword(rs.getString("password"))
                            .isAdmin(rs.getBoolean("isAdmin"))
                            .build())
                    .withCourse(null)
                    .build();


    private Announcement getMockAnnouncement() {
        Course mockCourse = new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(COURSE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();

        User mockUser = new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(USER_IS_ADMIN)
                .build();
        return new Announcement.Builder()
                .withAnnouncementId(ANNOUNCEMENT_ID)
                .withDate(ANNOUNCEMENT_DATE)
                .withTitle("test_title")
                .withContent("test_content")
                .withAuthor(mockUser)
                .withCourse(mockCourse)
                .build();
    }

    @Test
    public void testCreate() {
        Announcement announcement = getMockAnnouncement();
        announcement = announcementDao.create(announcement.getDate(), announcement.getTitle(),
                announcement.getContent(), announcement.getAuthor(), announcement.getCourse());
        assertEquals(1L, announcement.getAnnouncementId().longValue());
    }

    @Test
    public void testDelete() {
        assertTrue(announcementDao.delete(2L));
    }

    @Test
    public void testDeleteNoExist() {
        final Long NOT_EXISTING_ID = 100L;
        assertFalse(announcementDao.delete(NOT_EXISTING_ID));
    }


    @Test
    public void getById() {
        Optional<Announcement> announcementOptional = announcementDao.findById(2L);
        assertTrue(announcementOptional.isPresent());
        assertEquals(2L, announcementOptional.get().getAnnouncementId().longValue());
        assertEquals(COURSE_ID, announcementOptional.get().getCourse().getCourseId());
        assertEquals(USER_ID, announcementOptional.get().getAuthor().getUserId());
        assertEquals("test_title", announcementOptional.get().getTitle());
        assertEquals("test_content", announcementOptional.get().getContent());
    }

    @Test
    public void getByIdNoExist() {
        final Long NOT_EXISTING_ID = 100L;
        Optional<Announcement> announcementOptional = announcementDao.findById(NOT_EXISTING_ID);
        assertFalse(announcementOptional.isPresent());
    }




    @Test
    public void testListCourseAnnouncementsNonExistentId() {
        List<Announcement> list = announcementDao.listByCourse(COURSE_ID + 1, new CampusPageRequest(PAGE, PAGE_SIZE)).getContent();
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void testUpdate() {
        Announcement announcement = getMockAnnouncement();
        announcement.setTitle("test_update_title");
        announcement.setContent("test_update_content");
        final boolean isUpdated = announcementDao.update(2L, announcement);
        assertTrue(isUpdated);
    }


}
