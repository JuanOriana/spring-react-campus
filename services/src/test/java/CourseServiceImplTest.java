import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Errors;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.exception.DuplicateCourseException;
import ar.edu.itba.paw.services.CourseServiceImpl;
import ar.edu.itba.paw.services.TimetableServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;

import java.sql.Time;
import java.time.Year;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    private static Long SUBJECT_ID = 1L;
    private static int YEAR = 2021;
    private static int QUARTER = 2;
    private static String BOARD = "S1";

    @InjectMocks
    private final CourseServiceImpl courseService = new CourseServiceImpl();

    @Mock
    private CourseDao mockDao;

    @Test(expected = DuplicateCourseException.class)
    public void testCreateCourseDuplicated() {
        when(mockDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID)).thenThrow(DuplicateKeyException.class);
        courseService.create(YEAR, QUARTER, BOARD, SUBJECT_ID, Collections.emptyList(), Collections.emptyList());
        Assert.fail("Should have thrown DuplicateCourseException");
    }

}
