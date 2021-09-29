package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Announcement> COURSE_ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
        new Announcement.Builder()
            .withAnnouncementId(rs.getLong("announcementid"))
            .withDate(rs.getTimestamp("date").toLocalDateTime())
            .withTitle(rs.getString("title"))
            .withContent(rs.getString("content"))
            .withAuthor(new User.Builder()
                    .withUserId(rs.getLong("userId"))
                    .withFileNumber(rs.getInt("fileNumber"))
                    .withName(rs.getString("name"))
                    .withSurname(rs.getString("surname"))
                    .withUsername(rs.getString("username"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .isAdmin(rs.getBoolean("isAdmin"))
                    .build())
            .withCourse(new Course.Builder()
                    .withCourseId(rs.getLong("courseId"))
                    .withYear(rs.getInt("year"))
                    .withQuarter(rs.getInt("quarter"))
                    .withBoard(rs.getString("board"))
                    .withSubject(new Subject(rs.getLong("subjectId"), rs.getString("code"),
                            rs.getString("subjectName")))
                    .build())
            .build();


    @Autowired
    public AnnouncementDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcements").usingGeneratedKeyColumns("announcementid");
    }

    @Override
    public Announcement create(LocalDateTime date, String title, String content, User author, Course course) {
        final Map<String, Object> args = new HashMap<>();
        args.put("date", Timestamp.valueOf(date));
        args.put("title",title);
        args.put("content", content);
        args.put("userId", author.getUserId());
        args.put("courseId", course.getCourseId());
        final Long announcementId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new Announcement.Builder()
            .withAnnouncementId(announcementId)
            .withDate(date)
            .withTitle(title)
            .withContent(content)
            .withAuthor(author)
            .withCourse(course)
        .build();
    }

    @Override
    public boolean update(Long id, Announcement announcement) {
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
    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM announcements WHERE announcementId = ?", new Object[]{id}) == 1;
    }

    @Override
    public Optional<Announcement> getById(Long id) {
        return jdbcTemplate.query("SELECT * " +
                "FROM announcements NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN users " +
                "WHERE announcementId = ?", new Object[]{id}, COURSE_ANNOUNCEMENT_ROW_MAPPER).stream().findFirst();
    }

    private int getTotalPageCount(String query, Object[] queryParams, Integer pageSize) {
        String rowCountSql = "SELECT count(1) AS row_count FROM (" + query + ") as foo";
        int rowCount = jdbcTemplate.queryForObject(
                rowCountSql,
                queryParams, (rs, rowNum) -> rs.getInt(1)
        );
        return (int) Math.ceil((double)rowCount / pageSize);
    }

    private CampusPage<Announcement> listBy(String selectQuery, Long property, CampusPageRequest pageRequest) {
        int pageCount = getTotalPageCount(selectQuery, new Object[]{property}, pageRequest.getPageSize());
        if(pageCount == 0) return new CampusPage<>();
        if(pageRequest.getPage() > pageCount) throw new PaginationArgumentException();
        String paginationQuerySql = selectQuery + " LIMIT " + pageRequest.getPageSize() + " " + "OFFSET "
                + (pageRequest.getPage() - 1) * pageRequest.getPageSize();
        List<Announcement> content = jdbcTemplate.query(
                paginationQuerySql,
                new Object[]{property}, COURSE_ANNOUNCEMENT_ROW_MAPPER);
        return new CampusPage<>(content, pageRequest.getPageSize(), pageRequest.getPage(), pageCount);
    }

    @Override
    public CampusPage<Announcement> listByUser(Long userId, CampusPageRequest pageRequest) {
        String selectQuery = "SELECT * " +
                "FROM announcements NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN users NATURAL JOIN user_to_course " +
                "WHERE courseId IN (SELECT courseId FROM user_to_course WHERE userid = ?) " +
                "ORDER BY date DESC";
       return listBy(selectQuery, userId, pageRequest);
    }

    @Override
    public CampusPage<Announcement> listByCourse(Long courseId, CampusPageRequest pageRequest) {
        String selectQuery = "SELECT * " +
                "FROM announcements NATURAL JOIN courses NATURAL JOIN subjects NATURAL JOIN users NATURAL JOIN user_to_course " +
                "WHERE courseId = ?" +
                "ORDER BY date DESC";
        return listBy(selectQuery, courseId, pageRequest);
    }

}
