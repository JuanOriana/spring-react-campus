package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserDaoImplTest {
    private final int FILE_NUMBER = 1;
    private final String NAME = "John";
    private final String SURNAME = "Doe";
    private final String USERNAME = "johndoe";
    private final String EMAIL = "johndoe@lorem.com";
    private final String PASSWORD = "d8d3aedd4b5d0ce0131600eaadc48dcb";
    private final boolean IS_ADMIN = true;
    @Autowired
    private DataSource ds;

    @Autowired
    UserDaoImpl userDao;

    private JdbcTemplate jdbcTemplate;
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        User user = userDao.create(new User(FILE_NUMBER, NAME, SURNAME, USERNAME, EMAIL, PASSWORD, IS_ADMIN));
        assertNotNull(user);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

}
