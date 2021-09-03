import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.services.CourseServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    private static long COURSE_ID = 1;
    private static long INVALID_COURSE_ID = 999;
    private static int YEAR = 2021;
    private static int QUARTER = 2;
    private static String CODE = "122A";
    private static String BOARD = "S1";
    private static String NAME = "Proyecto de Aplicaciones Web";
    private Course getMockCourse() {
        Course mock = new Course(YEAR, CODE, QUARTER, BOARD, NAME);
        mock.setCourseId(COURSE_ID);
        return mock;
    }

    @InjectMocks
    private CourseServiceImpl courseService = new CourseServiceImpl();

    @Mock
    private CourseDao mockDao;

    @Test
    public void testCreateCourse() {
        Course course = getMockCourse();
        when(mockDao.create(eq(course))).thenReturn(true);
        boolean courseCreateResult = courseService.create(course);
        Assert.assertTrue(courseCreateResult);
    }

    @Test
    public void testFindByCourseId() {
        Course course = getMockCourse();
        when(mockDao.getById(eq(COURSE_ID))).thenReturn(Optional.of(course));
        final Optional<Course> queriedCourse = courseService.getById(COURSE_ID);
        Assert.assertTrue(queriedCourse.isPresent());
        Assert.assertEquals(COURSE_ID, queriedCourse.get().getCourseId());
    }

    @Test(expected =  RuntimeException.class)
    public void testCreateCourseDuplicate() {
        Course course = getMockCourse();
        when(mockDao.create(eq(course))).thenThrow(new RuntimeException());
        boolean courseCreateResult = courseService.create(course);
        Assert.fail("Should have thrown runtime exception for duplicate course creation");
    }

    @Test
    public void testUpdate() {
        Course course = getMockCourse();
        when(mockDao.update(eq(COURSE_ID), eq(course))).thenReturn(true);
        boolean courseUpdateResult = courseService.update(COURSE_ID, course);
        Assert.assertTrue(courseUpdateResult);
    }

    @Test
    public void testUpdateDoesNotExist() {
        Course course = getMockCourse();
        when(mockDao.update(eq(INVALID_COURSE_ID), eq(course))).thenReturn(false);
        boolean courseUpdateResult = courseService.update(INVALID_COURSE_ID, course);
        Assert.assertFalse(courseUpdateResult);
    }

    @Test
    public void testDelete() {
        when(mockDao.delete(eq(COURSE_ID))).thenReturn(true);
        boolean courseUpdateResult = courseService.delete(COURSE_ID);
        Assert.assertTrue(courseUpdateResult);
    }

    @Test
    public void testDeleteDoesNotExit() {
        when(mockDao.delete(eq(INVALID_COURSE_ID))).thenReturn(false);
        boolean courseUpdateResult = courseService.delete(INVALID_COURSE_ID);
        Assert.assertFalse(courseUpdateResult);
    }

    @Test
    public void testList() {
        Course course = getMockCourse();
        when(mockDao.list()).thenReturn(new ArrayList<Course>(){{ add(course); }});
        List<Course> courses = courseService.list();
        Assert.assertTrue(courses.size() > 0);
        Assert.assertEquals(course.getCourseId(), courses.get(0).getCourseId());
    }

}
