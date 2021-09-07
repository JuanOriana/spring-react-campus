package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TimeTableDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private TimetableDaoImpl timetableDao;

    private JdbcTemplate jdbcTemplate;

    private final int COURSE_ID = 1;
    private final int COURSE_YEAR = 2021;
    private final int COURSE_QUARTER = 2;
    private final String COURSE_BOARD = "S1";

    private final int SUBJECT_ID = 1;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "PAW";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
        String sqlInsertSubject = String.format("INSERT INTO subjects  VALUES (%d, %s, %s)", SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        String sqlInsertCourse = String.format("INSERT INTO courses  VALUES (%d, %d, %d,%s, %d)", COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        jdbcTemplate.execute(sqlInsertSubject);
        jdbcTemplate.execute(sqlInsertCourse);
    }

    @Test
    public void testCreate() {
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateNonExistentCourseId() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testUpdateNoExist() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testDeleteNoExist() {
    }

    @Test
    public void testGetById() {
    }

    @Test
    public void getByIdNoExist() {
    }

}
