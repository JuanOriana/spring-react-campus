package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
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
    private static  final RowMapper<Announcement> ROW_MAPPER = (rs, rowNum) -> new Announcement(rs.getLong("announcementId"),rs.getLong("teacherId"),rs.getLong("subjectId"),rs.getDate("date"),rs.getString("title"),rs.getString("content"));

    @Autowired
    public AnnouncementDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("announcement");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  announcement ( "+
                "announcementId INTEGER,"+
                "teacherId INTEGER,"+
                "subjectId INTEGER," +
                "title varchar (50), "+
                "content varchar, "+
                "date DATE, "+
                "PRIMARY KEY(announcementId, teacherId, subjectId) )");
    }

    @Override
    public boolean create(Announcement announcement) {
        final Map<String,Object> args = new HashMap<>();
        args.put("announcementId",announcement.getAnnouncementId());
        args.put("subjectId",announcement.getSubjectId());
        args.put("date",announcement.getDate());
        args.put("title",announcement.getTitle());
        args.put("content",announcement.getContent());
        args.put("teacherId",announcement.getTeacherId());

        final Number rowsAffected = jdbcInsert.execute(args);

        return rowsAffected.intValue() >0;
    }

    @Override
    public boolean update(int id, Announcement announcement) {
        return false;
    }

    @Override
    public boolean delete(int id) {
//        final Number rowsAffected = jdbcTemplate.que;
//
//        final Number rowsAffected = jdbcInsert.execute(args);
//
//        return rowsAffected.intValue() >0;
        return false;
    }

    @Override
    public List<Announcement> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM announcement", ROW_MAPPER));
    }

    @Override
    public List<Announcement> listByCourse(int courseId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM announcement WHERE subjectId = ?",new Object[]{courseId}, ROW_MAPPER));
    }

    @Override
    public Optional<Announcement> getById(int id) {
        return jdbcTemplate.query("SELECT * FROM announcement WHERE announcementId = ?",new Object[]{id},ROW_MAPPER).stream().findFirst();
    }
}
