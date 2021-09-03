package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Teacher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

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
    private int totalRowInTable;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"courses");    }

    @Test
    public void testCreate(){
        final boolean isCreated = courseDao.create(new Course(2020,"a1",2,"C","F1"));

        assertEquals(true,isCreated);
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"courses"));
    }

    @Test
    public void testCreateDuplicateId(){

        final boolean isCreated1 = courseDao.create(new Course(2020,"a1",2,"C","F1"));
        final boolean isCreated2 = courseDao.create(new Course(2020,"a1",2,"C","F1"));

        assertEquals(true,isCreated1);
        assertEquals(false,isCreated2);
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"courses"));
    }


    @Test
    public void testDelete(){
        jdbcTemplate.execute("INSERT INTO courses  VALUES (1,'test_name','test_code',1,'test_board',2021)");
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"courses"));

        courseDao.delete(1);
        assertEquals(0,JdbcTestUtils.countRowsInTable(jdbcTemplate,"courses"));
    }

    @Test
    public void testDeleteNoExist(){
        jdbcTemplate.execute("INSERT INTO courses (name,code,quarter,board,year) VALUES ('test_name','test_code',1,'test_board',2021)");

        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"courses"));
        assertFalse(courseDao.delete(11)); // magic number
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"courses"));
    }

    @Test
    public void testGetById(){
        jdbcTemplate.execute("INSERT INTO courses VALUES (10,'test_name','test_code',1,'test_board',2021)");
        final Optional<Course> course = courseDao.getById(10);
        assertNotNull(course);
        assertEquals(true,course.isPresent());
        assertEquals("test_name",course.get().getName());
        assertEquals("test_code",course.get().getCode());
        assertEquals(Optional.of(1).get(), course.get().getQuarter());
        assertEquals("test_board",course.get().getBoard());
        assertEquals(Optional.of(2021).get(),course.get().getYear());
    }

    @Test
    public void testGetByIdNoExist(){
        jdbcTemplate.execute("INSERT INTO courses (name,code,quarter,board,year) VALUES ('test_name','test_code',1,'test_board',2021)");
        final Optional<Course> course = courseDao.getById(10);
        assertNotNull(course);
        assertEquals(false,course.isPresent());
    }
    @Test
    public void testList(){
        jdbcTemplate.execute("INSERT INTO courses (name,code,quarter,board,year) VALUES ('test_name','test_code',1,'test_board',2021)");
        final List<Course> list = courseDao.list();
        assertNotNull(list);
        assertEquals(1,list.size());
    }
    @Test
    public void testEmptyList(){
        final List<Course> list = courseDao.list();
        assertNotNull(list);
        assertEquals(0,list.size());
    }

}
