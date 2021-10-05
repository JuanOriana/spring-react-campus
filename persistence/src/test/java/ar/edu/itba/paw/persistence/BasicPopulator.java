package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class BasicPopulator {

    @Autowired
    private DataSource ds;
    protected JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }
    protected void insertSubject(Long subjectId, String subjectName, String code) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("subjects");
        Map<String, Object> args = new HashMap<>();
        args.put("subjectId", subjectId);
        args.put("subjectName", subjectName);
        args.put("code", code);
        subjectJdbcInsert.execute(args);
    }

    protected void insertCourse(Long courseId, Long subjectId, int quarter, String board, int year) {
        SimpleJdbcInsert courseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
        Map<String, Object> args = new HashMap<>();
        args.put("courseId", courseId);
        args.put("subjectId", subjectId);
        args.put("quarter", quarter);
        args.put("board", board);
        args.put("year", year);
        courseJdbcInsert.execute(args);
    }

    protected void insertUser(Long userId, int fileNumber, String name, String surname, String username, String email,
                            String password, boolean isAdmin) {
        SimpleJdbcInsert userJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
        SimpleJdbcInsert profileImageJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("profile_images");
        Map<String, Object> args = new HashMap<>();
        args.put("userId",userId);
        args.put("fileNumber",fileNumber);
        args.put("name",name);
        args.put("surname",surname);
        args.put("username",username);
        args.put("email",email);
        args.put("password",password);
        args.put("isAdmin",isAdmin);
        userJdbcInsert.execute(args);
        Map<String,Object> argsProfileImage = new HashMap<>();
        argsProfileImage.put("image", null);
        argsProfileImage.put("userid", userId);
        profileImageJdbcInsert.execute(argsProfileImage);
    }

    protected void insertAnnouncement(Long announcementId, Long userId, Long courseId, String title, String content, LocalDateTime date) {
        SimpleJdbcInsert announcementJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcements");
        Map<String, Object> args = new HashMap<>();
        args.put("announcementId",announcementId);
        args.put("userId",userId);
        args.put("courseId",courseId);
        args.put("title",title);
        args.put("content",content);
        args.put("date", Timestamp.valueOf(date));
        announcementJdbcInsert.execute(args);
    }

    protected void insertRole(int roleId, String roleName) {
        SimpleJdbcInsert roleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
        Map<String, Object> args = new HashMap<>();
        args.put("roleId", roleId);
        args.put("roleName", roleName);
        roleJdbcInsert.execute(args);
    }

    protected void insertUserToCourse(Long userId, Long courseId, int roleId) {
        SimpleJdbcInsert userToCourseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_to_course");
        Map<String, Object> args = new HashMap<>();
        args.put("userId",userId);
        args.put("courseId",courseId);
        args.put("roleId",roleId);
        userToCourseJdbcInsert.execute(args);
    }
}
