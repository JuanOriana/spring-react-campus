package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Announcement> COURSE_ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
        new Announcement(rs.getInt("announcementId"), rs.getDate("date"), rs.getString("title"),
                rs.getString("content"), new User(rs.getInt("userId"), rs.getInt("fileNumber"),
                rs.getString("name"), rs.getString("surname"), null, null, null, rs.getBoolean("isAdmin")),
                new Course(rs.getInt("courseId"), rs.getInt("year"), rs.getInt("quarter"),
                        rs.getString("board"), new Subject(rs.getInt("subjectId"), rs.getString("code"),
                        rs.getString("subjectName"))));
    ;

    private static final RowMapper<Announcement> ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
            new Announcement(rs.getInt("announcementId"), rs.getDate("date"), rs.getString("title"),
                    rs.getString("content"), new User(rs.getInt("userId"), rs.getInt("fileNumber"),
                    rs.getString("name"), rs.getString("surname"), null, null,
                    null, rs.getBoolean("isAdmin")),null);

    @Autowired
    public AnnouncementDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcements").usingGeneratedKeyColumns("announcementId");
    }

    @Override
    public Announcement create(Announcement announcement) {
        final Map<String, Object> args = new HashMap<>();
        args.put("date", announcement.getDate());
        args.put("title", announcement.getTitle());
        args.put("content", announcement.getContent());
        args.put("userId", announcement.getAuthor().getUserId());
        args.put("courseId", announcement.getCourse().getCourseId());
        final int announcementId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new Announcement(announcementId, announcement.getDate(), announcement.getTitle(), announcement.getContent(),
                announcement.getAuthor(), announcement.getCourse());
    }

    @Override
    public boolean update(long id, Announcement announcement) {
        return jdbcTemplate.update("UPDATE announcements " +
                "SET userId = ?," +
                "courseId = ?," +
                "date = ?," +
                "title = ?," +
                "content = ?" +
                "WHERE announcementId = ?", new Object[]{announcement.getAuthor().getUserId(), announcement.getCourse().getCourseId(),
                announcement.getDate(), announcement.getTitle(), announcement.getContent(), id}) == 1;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM announcements WHERE announcementId = ?", new Object[]{id}) == 1;
    }

    @Override
    public List<Announcement> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT announcementId, date, title, content, userId, fileNumber, name, " +
                "surname, isAdmin, courseId, year, quarter, board, subjectId, code, subjectName " +
                "FROM announcements NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN users", COURSE_ANNOUNCEMENT_ROW_MAPPER));
    }


    public List<Announcement> listByCourse(long courseId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM announcements NATURAL JOIN users WHERE courseId = ?",
                new Object[]{courseId}, ANNOUNCEMENT_ROW_MAPPER));
    }

    @Override
    public Optional<Announcement> getById(long id) {
        return jdbcTemplate.query("SELECT announcementId, date, title, content, userId, fileNumber, name, " +
                "surname, isAdmin, courseId, year, quarter, board, subjectId, code, subjectName " +
                "FROM announcements NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN users " +
                "WHERE announcementId = ?", new Object[]{id}, COURSE_ANNOUNCEMENT_ROW_MAPPER).stream().findFirst();
    }
}
