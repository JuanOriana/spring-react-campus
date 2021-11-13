import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.DuplicateCourseException;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
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

    @InjectMocks
    private final CourseServiceImpl courseService = new CourseServiceImpl();

    @Mock
    private CourseDao courseDao;

    @Test(expected = PaginationArgumentException.class)
    public void testListByYearQuarterPaginationExceptionNegativePage() {
        Course course = BaseServiceTestUtil.getMockCourse();
        CampusPageRequest pageRequest = new CampusPageRequest(BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_SIZE);
        when(courseDao.listByYearQuarter(BaseServiceTestUtil.COURSE_YEAR, BaseServiceTestUtil.COURSE_QUARTER, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Course>() {{
                    add(course);
                }},
                        BaseServiceTestUtil.PAGE_SIZE, BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_TOTAL));
        courseService.listByYearQuarter(BaseServiceTestUtil.COURSE_YEAR, BaseServiceTestUtil.COURSE_QUARTER, BaseServiceTestUtil.NEGATIVE_PAGE, BaseServiceTestUtil.PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }

    @Test(expected = PaginationArgumentException.class)
    public void testListByYearQuarterPaginationExceptionNegativePageSize() {
        Course course = BaseServiceTestUtil.getMockCourse();
        CampusPageRequest pageRequest = new CampusPageRequest(BaseServiceTestUtil.PAGE, BaseServiceTestUtil.NEGATIVE_PAGE_SIZE);
        when(courseDao.listByYearQuarter(BaseServiceTestUtil.COURSE_YEAR, BaseServiceTestUtil.COURSE_QUARTER, pageRequest))
                .thenReturn(new CampusPage<>(new ArrayList<Course>() {{
                    add(course);
                }},
                        BaseServiceTestUtil.NEGATIVE_PAGE_SIZE, BaseServiceTestUtil.PAGE, BaseServiceTestUtil.PAGE_TOTAL));
        courseService.listByYearQuarter(BaseServiceTestUtil.COURSE_YEAR, BaseServiceTestUtil.COURSE_QUARTER, BaseServiceTestUtil.PAGE, BaseServiceTestUtil.NEGATIVE_PAGE_SIZE);
        Assert.fail("Should have thrown PaginationArgumentException");
    }
}
