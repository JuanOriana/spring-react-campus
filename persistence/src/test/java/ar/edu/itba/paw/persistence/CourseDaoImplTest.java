package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/course_populator.sql")
@Rollback
@Transactional
public class CourseDaoImplTest extends BasicPopulator {

    @Autowired
    private CourseDao courseDao;


    @Test
    public void testCreate() {
        Course course = courseDao.create(COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, SUBJECT_ID);
        assertNotNull(course);
    }


    @Test
    public void testDelete() {
        assertTrue(courseDao.delete(COURSE_ID));
    }

    @Test
    public void testDeleteNoExist() {
        assertFalse(courseDao.delete(INVALID_COURSE_ID));
    }

    @Test
    public void testGetById() {
        final Optional<Course> course = courseDao.findById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(COURSE_ID, course.get().getCourseId());
    }

    @Test
    public void testGetByIdNoExist() {
        final Optional<Course> course = courseDao.findById(INVALID_COURSE_ID);
        assertNotNull(course);
        assertFalse(course.isPresent());
    }


    @Test
    public void testUpdate() {
        Course course = new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(UPDATE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
        assertTrue(courseDao.update(COURSE_ID, course));
    }

    @Test
    public void testGetStudents() {
        List<User> list = courseDao.getStudents(COURSE_ID);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(USER_ID, list.get(0).getUserId());
    }

    @Test
    public void testEnroll() {
        assertTrue(courseDao.enroll(USER_ID, COURSE_ID, STUDENT_ROLE_ID));
    }

}
