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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnouncementServiceImplTest {
    private static final Long ANNOUNCEMENT_ID = 1L;
    private static final Long INVALID_ANNOUNCEMENT_ID = 999L;
    private final Long USER_ID = 1L;
    private final int USER_FILE_NUMBER = 41205221;
    private final String USER_NAME = "Paw";
    private final String USER_SURNAME = "2021";
    private final String USER_USERNAME = "paw2021";
    private final String USER_EMAIL = "paw2021@itba.edu.ar";
    private final String USER_PASSWORD = "asd123";
    private final Integer PAGE_SIZE = 1;
    private final Integer PAGE = 0;

    private final Long COURSE_ID = 1L;
    private final int COURSE_YEAR = 2021;
    private final int COURSE_QUARTER = 2;
    private final String COURSE_BOARD = "S1";

    private final Long SUBJECT_ID = 1L;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "Protos";

    private static final LocalDateTime ANNOUNCEMENT_DATE = LocalDateTime.now();
    private static final String ANNOUNCEMENT_TITLE = "Unit Testing";
    private static final String ANNOUNCEMENT_CONTENT = "Rocks! (or not)";

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
                .isAdmin(true)
                .build();

        return new Announcement.Builder()
            .withAnnouncementId(ANNOUNCEMENT_ID)
            .withDate(ANNOUNCEMENT_DATE)
            .withTitle(ANNOUNCEMENT_TITLE)
            .withContent(ANNOUNCEMENT_CONTENT)
            .withAuthor(mockUser)
            .withCourse(mockCourse)
        .build();
    }

    @Mock
    private AnnouncementDao mockDao;

    @InjectMocks
    private final AnnouncementServiceImpl announcementService = new AnnouncementServiceImpl();

    @Test
    public void testCreateAnnouncement() {
        Announcement announcement = getMockAnnouncement();
        when(mockDao.create(any(LocalDateTime.class), anyString(), anyString(),
                any(User.class), any(Course.class))).thenReturn(announcement);
        final Announcement newAnnouncement = announcementService.create(ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT,
                announcement.getAuthor(), announcement.getCourse());
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
        when(mockDao.create(any(LocalDateTime.class), anyString(), anyString(),
                any(User.class), any(Course.class))).thenThrow(RuntimeException.class);
        announcementService.create(ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT,
                announcement.getAuthor(), announcement.getCourse());
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
        when(mockDao.findAnnouncementByPage(USER_ID, PageRequest.of(PAGE, PAGE_SIZE)))
                .thenReturn(new PageImpl<>(new ArrayList<Announcement>(){{ add(announcement); }}));
        List<Announcement> announcements = announcementService.findAnnouncementByPage(USER_ID, PageRequest.of(PAGE, PAGE_SIZE)).toList();
        Assert.assertTrue(announcements.size() > 0);
        Assert.assertEquals(announcement.getCourse().getCourseId(), announcements.get(0).getCourse().getCourseId());
    }

}
