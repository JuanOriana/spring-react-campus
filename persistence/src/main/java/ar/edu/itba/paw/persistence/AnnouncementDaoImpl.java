package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
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
    private static final RowMapper<Announcement> ROW_MAPPER = (rs, rowNum) -> {
        Announcement announcement = new Announcement(rs.getLong("teacherId"), rs.getLong("courseId"), rs.getDate("date"), rs.getString("title"), rs.getString("content"));
        announcement.setAnnouncementId(rs.getLong("announcementId"));
        return announcement;
    };

    @Autowired
    public AnnouncementDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcements");
    }

    @Override
    public boolean create(Announcement announcement) {
        final Map<String, Object> args = new HashMap<>();
        args.put("date", announcement.getDate());
        args.put("title", announcement.getTitle());
        args.put("content", announcement.getContent());
        args.put("teacherId", announcement.getTeacherId());

        final Number rowsAffected = jdbcInsert.execute(args);

        return rowsAffected.intValue() > 0;
    }

    @Override
    public boolean update(long id, Announcement announcement) {
        return jdbcTemplate.update("UPDATE announcements " +
                "SET teacherId = ?," +
                "courseId = ?," +
                "date = ?," +
                "title = ?," +
                "content = ?" +
                "WHERE announcementId = ?", new Object[]{announcement.getTeacherId(), announcement.getcourseId(), announcement.getDate(), announcement.getTitle(), announcement.getContent(), id}) == 1;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM announcements WHERE announcementId = ?", new Object[]{id}) == 1;
    }

    @Override
    public List<Announcement> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM announcements", ROW_MAPPER));
    }


    public List<Announcement> listByCourse(long courseId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM announcements WHERE courseId = ?",new Object[]{courseId}, ROW_MAPPER));
    }

    @Override
    public Optional<Announcement> getById(long id) {
        return jdbcTemplate.query("SELECT * FROM announcements WHERE announcementId = ?",new Object[]{id},ROW_MAPPER).stream().findFirst();
    }
}
