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
    private final Long DB_ANNOUNCEMENT_ID = 2L;
    private final String DB_ANNOUNCEMENT_TITLE = "test_title";
    private final String DB_ANNOUNCEMENT_CONTENT = "test_content";


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
                .withTitle(DB_ANNOUNCEMENT_TITLE)
                .withContent(DB_ANNOUNCEMENT_CONTENT)
                .withAuthor(mockUser)
                .withCourse(mockCourse)
                .build();
    }

    @Test
    public void testCreate() {
        Announcement announcement = getMockAnnouncement();
        announcement = announcementDao.create(announcement.getDate(), announcement.getTitle(),
                announcement.getContent(), announcement.getAuthor(), announcement.getCourse());
        assertEquals(ANNOUNCEMENT_ID, announcement.getAnnouncementId());
    }

    @Test
    public void testDelete() {
        assertTrue(announcementDao.delete(DB_ANNOUNCEMENT_ID));
    }

    @Test
    public void testDeleteNoExist() {
        final Long NOT_EXISTING_ID = 100L;
        assertFalse(announcementDao.delete(NOT_EXISTING_ID));
    }


    @Test
    public void findById() {
        Optional<Announcement> announcementOptional = announcementDao.findById(DB_ANNOUNCEMENT_ID);
        assertTrue(announcementOptional.isPresent());
        assertEquals(DB_ANNOUNCEMENT_ID, announcementOptional.get().getAnnouncementId());
        assertEquals(COURSE_ID, announcementOptional.get().getCourse().getCourseId());
        assertEquals(USER_ID, announcementOptional.get().getAuthor().getUserId());
        assertEquals(DB_ANNOUNCEMENT_TITLE, announcementOptional.get().getTitle());
        assertEquals(DB_ANNOUNCEMENT_CONTENT, announcementOptional.get().getContent());
    }

    @Test
    public void findByIdNoExist() {
        final Long NOT_EXISTING_ID = 100L;
        Optional<Announcement> announcementOptional = announcementDao.findById(NOT_EXISTING_ID);
        assertFalse(announcementOptional.isPresent());
    }




    @Test
    public void testListCourseAnnouncementsNonExistentId() {
        final Long NOT_EXISTING_COURSE_ID = 100L;
        List<Announcement> list = announcementDao.listByCourse(NOT_EXISTING_COURSE_ID, new CampusPageRequest(PAGE, PAGE_SIZE)).getContent();
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void testUpdate() {
        Announcement announcement = getMockAnnouncement();
        announcement.setTitle("test_update_title");
        announcement.setContent("test_update_content");
        final boolean isUpdated = announcementDao.update(DB_ANNOUNCEMENT_ID, announcement);
        assertTrue(isUpdated);
    }


}
