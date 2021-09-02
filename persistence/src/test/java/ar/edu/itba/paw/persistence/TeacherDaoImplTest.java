package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Teacher;
import org.junit.After;
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

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

// In this tests, we have the teacher database populated with (1,'test_name','test_surname','test_email','test_username','test_password')

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TeacherDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private TeacherDaoImpl teacherDao;

    private JdbcTemplate jdbcTemplate;
    private int totalRowsInTable;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        totalRowsInTable = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
    }

    @After
    public void cleanDB(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teachers");
    }


    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teachers");
        final boolean isCreated = teacherDao.create(new Teacher(1, "name", "surname", "mail", "username", "password"));
        assertEquals(true, isCreated);
        assertEquals( 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers"));
    }


    @Test
    public void testCreateDuplicateId(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teachers");
        final boolean isCreated1 = teacherDao.create(new Teacher(1,"name","surname","mail","username","password"));
        final boolean isCreated2 = teacherDao.create(new Teacher(1,"name","surname","mail","username","password"));

        assertEquals(true,isCreated1);
        assertEquals(false,isCreated2);
        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"teachers"));
    }

    @Test
    public void testDelete(){
        assertEquals(totalRowsInTable,JdbcTestUtils.countRowsInTable(jdbcTemplate,"teachers"));
        teacherDao.delete(1);
        assertEquals(totalRowsInTable-1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"teachers"));
    }


    @Test
    public void testDeleteNoExist(){
        assertEquals(totalRowsInTable,JdbcTestUtils.countRowsInTable(jdbcTemplate,"teachers"));
        assertFalse(teacherDao.delete(11)); // magic number
        assertEquals(totalRowsInTable,JdbcTestUtils.countRowsInTable(jdbcTemplate,"teachers"));
    }

    @Test
    public void testGetById(){
        final Optional<Teacher> teacher = teacherDao.getById(1);

        assertNotNull(teacher);
        assertEquals(true,teacher.isPresent());
        assertEquals("test_name",teacher.get().getName());
        assertEquals("test_surname",teacher.get().getSurname());
        assertEquals("test_email",teacher.get().getEmail());
        assertEquals("test_username",teacher.get().getUsername());
        assertEquals("test_password",teacher.get().getPassword());
    }

    @Test
    public void testGetByIdNoExist(){
        final Optional<Teacher> teacher = teacherDao.getById(11); //magic number

        assertNotNull(teacher);
        assertEquals(false,teacher.isPresent());
    }

    @Test
    public void testList(){
        final List<Teacher> list = teacherDao.list();

        assertNotNull(list);
        assertEquals(1,list.size());

    }

    @Test
    public void testEmptyList(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teachers");
        final List<Teacher> list = teacherDao.list();

        assertNotNull(list);
        assertEquals(0,list.size());

    }

}
