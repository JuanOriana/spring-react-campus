package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Announcement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TimeTableDaoImplTest {

    @Before
    public void setUp() {
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
