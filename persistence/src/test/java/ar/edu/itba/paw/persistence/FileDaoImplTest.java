package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class FileDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private FileDao fileDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
    }

    @Test
    public void testCreate() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testDeleteNoExist() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void getByIdNoExist() {
    }

    @Test
    public void testList() {
    }

    @Test
    public void testUpdate() {
    }

}
