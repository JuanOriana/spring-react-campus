package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;

import ar.edu.itba.paw.models.Teacher;
import javafx.util.Pair;

import ar.edu.itba.paw.models.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class CourseDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private CourseDaoImpl courseDao;

    private JdbcTemplate jdbcTemplate;

    private final int COURSE_ID = 1;
    private final int SUBJECT_ID = 1;
    private final int QUARTER = 1;
    private final int YEAR = 2021;
    private final int ID = 10;
    private final int INVALID_ID = 999;
    private final String SUBJECT_NAME = "PAW";
    private final String SUBJECT_CODE = "A1";
    private final String BOARD = "S1";
    private final int INVALID_COURSE_ID = 999;
    private final String insertCourseWithIdSql = String.format("INSERT INTO courses VALUES (%d,%d, %d,'S1',%d)", COURSE_ID, SUBJECT_ID, QUARTER, YEAR);
    private final String insertCourseSql = String.format("INSERT INTO courses (subjectId, quarter,board,year) VALUES (%d, %d,'S1',%d)", SUBJECT_ID, QUARTER, YEAR);
    private final String insertSubjectSql = String.format("INSERT INTO subjects (subjectId,code,name) VALUES (%d,'A1','PAW')", SUBJECT_ID);

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "coursesroles");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teachers");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
    }

    @Test
    public void testCreate() {
        jdbcTemplate.execute(insertSubjectSql);
        Course course = courseDao.create(new Course(COURSE_ID, YEAR, QUARTER, BOARD, new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME)));
        assertNotNull(course);
        assertEquals(QUARTER, course.getQuarter().intValue());
        assertEquals(YEAR, course.getYear().intValue());
        assertEquals(BOARD, course.getBoard());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateDuplicateUniqueValues() {

        final Course isCreated1 = courseDao.create(new Course(YEAR, QUARTER, BOARD, new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME)));
        final Course isCreated2 = courseDao.create(new Course(YEAR, QUARTER, BOARD, new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME)));

        Assert.fail("Should have thrown Runtime Exception for duplicate constraint");
    }


    @Test
    public void testDelete() {
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseWithIdSql);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));

        courseDao.delete(COURSE_ID);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test
    public void testDeleteNoExist() {
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseSql);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
        assertFalse(courseDao.delete(INVALID_COURSE_ID)); // magic number
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test
    public void testGetById() {
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseWithIdSql);
        final Optional<Course> course = courseDao.getById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(COURSE_ID, course.get().getCourseId());
    }

    @Test
    public void testGetByIdNoExist() {
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseWithIdSql);
        final Optional<Course> course = courseDao.getById(INVALID_COURSE_ID);
        assertNotNull(course);
        assertFalse(course.isPresent());
    }

    @Test
    public void testList() {
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseSql);
        final List<Course> list = courseDao.list();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testEmptyList() {
        final List<Course> list = courseDao.list();
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void testUpdate() {
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseWithIdSql);
        assertTrue(courseDao.update(COURSE_ID, new Course(YEAR, QUARTER, BOARD, new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))));

        final Optional<Course> course = courseDao.getById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(Optional.of(QUARTER).get(), course.get().getQuarter());
        assertEquals("S1", course.get().getBoard());
        assertEquals(Optional.of(YEAR).get(), course.get().getYear());
    }

    @Test
    public void getTeachersFromCourse() {
        int teacher1Id = 10;
        int teacher2Id = 11;
        String sqlInsertTeacher1Rol = String.format("INSERT INTO coursesroles VALUES (%d,%d,'rol');", teacher1Id, COURSE_ID);
        String sqlInsertTeacher2Rol = String.format("INSERT INTO coursesroles VALUES (%d,%d,'rol');", teacher2Id, COURSE_ID);
        String sqlInsertTeacher1Id = String.format("INSERT INTO teachers VALUES (%d,'test_name1','test_surname','test_email1','test_username','test_password')", teacher1Id);
        String sqlInsertTeacher2Id = String.format("INSERT INTO teachers VALUES (%d,'test_name2','test_surname','test_email2','test_username','test_password')", teacher2Id);
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseWithIdSql); // Create a entry in course table
        jdbcTemplate.execute(sqlInsertTeacher1Id);   // Create a entry in teacher table
        jdbcTemplate.execute(sqlInsertTeacher2Id);   // Create a entry in teacher table
        jdbcTemplate.execute(sqlInsertTeacher1Rol); //Create a entry inn coursesroles table
        jdbcTemplate.execute(sqlInsertTeacher2Rol); //Create a entry inn coursesroles table

        List<Pair<Teacher, String>> teacherAndRoles = courseDao.getTeachersFromCourse(COURSE_ID);

        assertEquals(2, teacherAndRoles.size());
        Teacher teacher = teacherAndRoles.get(0).getKey();
        String rol = teacherAndRoles.get(0).getValue();

        assertEquals("test_name1", teacher.getName());
        assertEquals("rol", rol);
    }

    @Test
    public void addTeacherToCourseTest() {
        int teacherId = 10;
        String sqlInsertTeacherId = String.format("INSERT INTO teachers VALUES (%d,'test_name','test_surname','test_email1','test_username','test_password')", teacherId);
        jdbcTemplate.execute(sqlInsertTeacherId);
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseWithIdSql);
        assertTrue(courseDao.addTeacherToCourse(teacherId, COURSE_ID, "rol"));
    }

    @Test(expected = RuntimeException.class)
    public void addTeacherToInexistenceCourseTest() {
        int teacherId = 10;
        String sqlInsertTeacherId = String.format("INSERT INTO teachers VALUES (%d,'test_name','test_surname','test_email1','test_username','test_password')", teacherId);
        jdbcTemplate.execute(sqlInsertTeacherId);
        jdbcTemplate.execute(insertCourseWithIdSql);

        courseDao.addTeacherToCourse(teacherId, INVALID_ID, "rol");
        Assert.fail("Should have thrown runtime exception for inexistence foreing key 'course id' ");
    }

}
