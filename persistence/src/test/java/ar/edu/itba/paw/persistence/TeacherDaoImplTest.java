package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Teacher;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TeacherDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private TeacherDaoImpl teacherDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "teachers");
    }

    @Test
    public void testCreate() {
        final boolean isCreated = teacherDao.create(new Teacher(1, "name", "surname", "mail", "username", "password"));
        assertEquals(true, isCreated);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers"));
    }

    //TODO arreglar este test
//    @Test
//    public void testCreateDuplicateId(){
//        final boolean isCreated1 = teacherDao.create(new Teacher(1,"name","surname","mail","username","password"));
//        final boolean isCreated2 = teacherDao.create(new Teacher(1,"name","surname","mail","username","password"));
//
//        assertEquals(true,isCreated1);
//        assertEquals(false,isCreated2);
//        assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"teachers"));
//    }

}
