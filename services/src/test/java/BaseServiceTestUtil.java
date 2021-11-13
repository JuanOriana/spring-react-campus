import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;

public class BaseServiceTestUtil {

    private BaseServiceTestUtil() {
        // Cannot be instantiated!
    }

    public static final Long ANNOUNCEMENT_ID = 1L;
    public static final Long USER_ID = 1L;
    public static final int USER_FILE_NUMBER = 41205221;
    public static final String USER_NAME = "Paw";
    public static final String USER_SURNAME = "2021";
    public static final String USER_USERNAME = "paw2021";
    public static final String USER_EMAIL = "paw2021@itba.edu.ar";
    public static final String USER_PASSWORD = "asd123";
    public static final Integer PAGE_SIZE = 10;
    public static final Integer PAGE_TOTAL = 1;
    public static final Integer PAGE = 1;
    public static final Integer NEGATIVE_PAGE = -1;
    public static final Integer NEGATIVE_PAGE_SIZE = -1;

    public static final LocalDateTime ANNOUNCEMENT_DATE = LocalDateTime.now();
    public static final String ANNOUNCEMENT_TITLE = "Unit Testing";
    public static final String ANNOUNCEMENT_CONTENT = "Rocks! (or not)";

    public static final Long COURSE_ID = 1L;
    public static final int COURSE_YEAR = 2021;
    public static final int COURSE_QUARTER = 2;
    public static final String COURSE_BOARD = "S1";

    public static final Long SUBJECT_ID = 1L;
    public static final String SUBJECT_CODE = "A1";
    public static final String SUBJECT_NAME = "Protos";

    public static Course getMockCourse() {
        return new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(COURSE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
    }

    public static User getMockUser() {
        return new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(true)
                .build();
    }

    public static Announcement getMockAnnouncement() {
        Course mockCourse = getMockCourse();
        User mockUser = getMockUser();
        return new Announcement.Builder()
                .withAnnouncementId(ANNOUNCEMENT_ID)
                .withDate(ANNOUNCEMENT_DATE)
                .withTitle(ANNOUNCEMENT_TITLE)
                .withContent(ANNOUNCEMENT_CONTENT)
                .withAuthor(mockUser)
                .withCourse(mockCourse)
                .build();
    }

}
