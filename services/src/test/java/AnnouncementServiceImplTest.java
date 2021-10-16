import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import ar.edu.itba.paw.services.AnnouncementServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnnouncementServiceImplTest {
    private static final Long ANNOUNCEMENT_ID = 1L;
    private final Long USER_ID = 1L;
    private final int USER_FILE_NUMBER = 41205221;
    private final String USER_NAME = "Paw";
    private final String USER_SURNAME = "2021";
    private final String USER_USERNAME = "paw2021";
    private final String USER_EMAIL = "paw2021@itba.edu.ar";
    private final String USER_PASSWORD = "asd123";
    private final Integer PAGE_SIZE = 10;
    private final Integer PAGE_TOTAL = 1;
    private final Integer PAGE = 1;
    private final Integer NEGATIVE_PAGE = -1;
    private final Integer NEGATIVE_PAGE_SIZE = -1;

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
    private AnnouncementDao announcementDao;

    @InjectMocks
    private final AnnouncementServiceImpl announcementService = new AnnouncementServiceImpl();

    @Test(expected = PaginationArgumentException.class)
    public void testListByUserPaginationExceptionNegativePage() {
              Announcement announcement = getMockAnnouncement();
              CampusPageRequest pageRequest = new CampusPageRequest(NEGATIVE_PAGE, PAGE_SIZE);
              when(announcementDao.listByUser(USER_ID, pageRequest))
                              .thenReturn(new CampusPage<>(new ArrayList<Announcement>(){{ add(announcement); }},
                                      PAGE_SIZE, NEGATIVE_PAGE, PAGE_TOTAL));
              announcementService.listByUser(USER_ID, NEGATIVE_PAGE, PAGE_SIZE);
              Assert.fail("Should have thrown PaginationArgumentException");
    }

    @Test(expected = PaginationArgumentException.class)
    public void testListByUserPaginationExceptionNegativePageSize() {
        Announcement announcement = getMockAnnouncement();
        CampusPageRequest pageRequest = new CampusPageRequest(PAGE, NEGATIVE_PAGE_SIZE);
        when(announcementDao.listByUser(USER_ID, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Announcement>(){{ add(announcement); }},
                        NEGATIVE_PAGE_SIZE, PAGE, PAGE_TOTAL));
        announcementService.listByUser(USER_ID, NEGATIVE_PAGE_SIZE, PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }

    @Test(expected = PaginationArgumentException.class)
    public void testListByCoursePaginationExceptionNegativePage() {
        Announcement announcement = getMockAnnouncement();
        CampusPageRequest pageRequest = new CampusPageRequest(NEGATIVE_PAGE, PAGE_SIZE);
        when(announcementDao.listByCourse(USER_ID, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Announcement>(){{ add(announcement); }},
                        PAGE_SIZE, NEGATIVE_PAGE, PAGE_TOTAL));
        announcementService.listByCourse(USER_ID, NEGATIVE_PAGE, PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }

    @Test(expected = PaginationArgumentException.class)
    public void testListByCoursePaginationExceptionNegativePageSize() {
        Announcement announcement = getMockAnnouncement();
        CampusPageRequest pageRequest = new CampusPageRequest(PAGE, NEGATIVE_PAGE_SIZE);
        when(announcementDao.listByCourse(USER_ID, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Announcement>(){{ add(announcement); }},
                        NEGATIVE_PAGE_SIZE, PAGE, PAGE_TOTAL));
        announcementService.listByCourse(USER_ID, PAGE, NEGATIVE_PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }


}
