import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.AnnouncementServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnouncementServiceImplTest {
    private static final long ANNOUNCEMENT_ID = 1;
    private static final long INVALID_ANNOUNCEMENT_ID = 999;
    private final int USER_ID = 1;
    private final int USER_FILE_NUMBER = 41205221;
    private final String USER_NAME = "Paw";
    private final String USER_SURNAME = "2021";
    private final String USER_USERNAME = "paw2021";
    private final String USER_EMAIL = "paw2021@itba.edu.ar";
    private final String USER_PASSWORD = "asd123";
    private final long PAGE_SIZE = 1;
    private final long PAGE = 1;

    private final int COURSE_ID = 1;
    private final int COURSE_YEAR = 2021;
    private final int COURSE_QUARTER = 2;
    private final String COURSE_BOARD = "S1";

    private final int SUBJECT_ID = 1;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "Protos";

    private static final Date ANNOUNCEMENT_DATE = new Date(2323223232L);
    private static final String ANNOUNCEMENT_TITLE = "Unit Testing";
    private static final String ANNOUNCEMENT_CONTENT = "Rocks! (or not)";

    private Announcement getMockAnnouncement() {
        Subject mockSubject = new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        Course mockCourse = new Course(COURSE_ID, COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, mockSubject);
        User mockUser = new User(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, true);
        Announcement mockAnnouncement = new Announcement(ANNOUNCEMENT_DATE, ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, mockUser, mockCourse);
        mockAnnouncement.setAnnouncementId(ANNOUNCEMENT_ID);
        return mockAnnouncement;
    }

    @InjectMocks
    private AnnouncementServiceImpl announcementService = new AnnouncementServiceImpl();

    @Mock
    private AnnouncementDao mockDao;

    @Test
    public void testCreateAnnouncement() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.create(eq(announcement))).thenReturn(announcement);
        final Announcement newAnnouncement = announcementService.create(announcement);
        Assert.assertEquals(newAnnouncement.getAnnouncementId(), ANNOUNCEMENT_ID);
    }

    @Test
    public void testFindByCourseId() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.getById(eq(ANNOUNCEMENT_ID))).thenReturn(Optional.of(announcement));
        final Optional<Announcement> queriedAnnouncement = announcementService.getById(ANNOUNCEMENT_ID);

        Assert.assertTrue(queriedAnnouncement.isPresent());
        Assert.assertEquals(ANNOUNCEMENT_ID, queriedAnnouncement.get().getAnnouncementId());
    }

    @Test(expected =  RuntimeException.class)
    public void testCreateAnnouncementDuplicate() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.create(eq(announcement))).thenThrow(new RuntimeException());
        Announcement announcementCreateResult = announcementService.create(announcement);
        Assert.fail("Should have thrown runtime exception for duplicate announcement creation");
    }

    @Test
    public void testUpdate() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.update(eq(ANNOUNCEMENT_ID), eq(announcement))).thenReturn(true);
        boolean announcementUpdateResult = announcementService.update(ANNOUNCEMENT_ID, announcement);
        Assert.assertTrue(announcementUpdateResult);
    }

    @Test
    public void testUpdateDoesNotExist() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.update(eq(INVALID_ANNOUNCEMENT_ID), eq(announcement))).thenReturn(false);
        boolean announcementUpdateResult = announcementService.update(INVALID_ANNOUNCEMENT_ID, announcement);
        Assert.assertFalse(announcementUpdateResult);
    }

    @Test
    public void testDelete() {
        when(mockDao.delete(eq(ANNOUNCEMENT_ID))).thenReturn(true);
        boolean announcementUpdateResult = announcementService.delete(ANNOUNCEMENT_ID);
        Assert.assertTrue(announcementUpdateResult);
    }

    @Test
    public void testDeleteDoesNotExit() {
        when(mockDao.delete(eq(INVALID_ANNOUNCEMENT_ID))).thenReturn(false);
        boolean announcementUpdateResult = announcementService.delete(INVALID_ANNOUNCEMENT_ID);
        Assert.assertFalse(announcementUpdateResult);
    }

    @Test
    public void testList() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.list(PAGE, PAGE_SIZE)).thenReturn(new ArrayList<Announcement>(){{ add(announcement); }});
        List<Announcement> announcements = announcementService.list(PAGE, PAGE_SIZE);
        Assert.assertTrue(announcements.size() > 0);
        Assert.assertEquals(announcement.getCourse().getCourseId(), announcements.get(0).getCourse().getCourseId());
    }

}
