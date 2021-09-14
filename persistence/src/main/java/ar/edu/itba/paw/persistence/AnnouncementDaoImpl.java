package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Announcement> COURSE_ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
        new Announcement(rs.getInt("announcementid"), rs.getTimestamp("date").toLocalDateTime(), rs.getString("title"),
                rs.getString("content"),
                new User.Builder()
                    .withUserId(rs.getInt("userId"))
                    .withFileNumber(rs.getInt("fileNumber"))
                    .withName(rs.getString("name"))
                    .withSurname(rs.getString("surname"))
                    .withUsername(rs.getString("username"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .isAdmin(rs.getBoolean("isAdmin"))
                .build(),
                new Course.Builder()
                    .withCourseId(rs.getInt("courseId"))
                    .withYear(rs.getInt("year"))
                    .withQuarter(rs.getInt("quarter"))
                    .withBoard(rs.getString("board"))
                    .withSubject(new Subject(rs.getInt("subjectId"), rs.getString("code"),
                        rs.getString("subjectName")))
                .build());

    private static final RowMapper<Announcement> ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
            new Announcement(rs.getInt("announcementid"), rs.getTimestamp("date").toLocalDateTime(), rs.getString("title"),
                    rs.getString("content"), new User.Builder()
                    .withUserId(rs.getInt("userId"))
                    .withFileNumber(rs.getInt("fileNumber"))
                    .withName(rs.getString("name"))
                    .withSurname(rs.getString("surname"))
                    .withUsername(rs.getString("username"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .isAdmin(rs.getBoolean("isAdmin"))
                    .build(),null);

    @Autowired
    public AnnouncementDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcements").usingGeneratedKeyColumns("announcementid");
    }

    @Override
    public Announcement create(Announcement announcement) {
        final Map<String, Object> args = new HashMap<>();
        args.put("date", Timestamp.valueOf(announcement.getDate()));
        args.put("title", announcement.getTitle());
        args.put("content", announcement.getContent());
        args.put("userId", announcement.getAuthor().getUserId());
        args.put("courseId", announcement.getCourse().getCourseId());
        final int announcementId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new Announcement(announcementId, announcement.getDate(), announcement.getTitle(), announcement.getContent(),
                announcement.getAuthor(), announcement.getCourse());
    }

    @Override
    public boolean update(Integer id, Announcement announcement) {
        return jdbcTemplate.update("UPDATE announcements " +
                "SET userId = ?," +
                "courseId = ?," +
                "date = ?," +
                "title = ?," +
                "content = ?" +
                "WHERE announcementId = ?", new Object[]{announcement.getAuthor().getUserId(), announcement.getCourse().getCourseId(),
                Timestamp.valueOf(announcement.getDate()), announcement.getTitle(), announcement.getContent(), id}) == 1;
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update("DELETE FROM announcements WHERE announcementId = ?", new Object[]{id}) == 1;
    }

    @Override
    public int getPageCount(Integer pageSize) {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();  // not reusable
        jdbcTemplate.query("SELECT * FROM announcements", countCallback);
        return (int) Math.ceil((double)countCallback.getRowCount() / pageSize);
    }

    @Override
    public List<Announcement> list(Integer userId, Integer page, Integer pageSize) {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM announcements NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN users " +
                "NATURAL JOIN user_to_course " +
                "WHERE courseid IN (SELECT courseid FROM user_to_course WHERE userid = ?) " +
                "LIMIT ? OFFSET ?",new Object[]{ userId, pageSize, (page - 1) * pageSize }, COURSE_ANNOUNCEMENT_ROW_MAPPER));
    }


    public List<Announcement> listByCourse(Integer courseId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM announcements NATURAL JOIN users WHERE courseId = ?",
                new Object[]{courseId}, ANNOUNCEMENT_ROW_MAPPER));
    }

    @Override
    public Optional<Announcement> getById(Integer id) {
        return jdbcTemplate.query("SELECT * " +
                "FROM announcements NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN users " +
                "WHERE announcementId = ?", new Object[]{id}, COURSE_ANNOUNCEMENT_ROW_MAPPER).stream().findFirst();
    }
}
