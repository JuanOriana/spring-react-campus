package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimetableDaoImpl implements TimetableDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Timetable> TIMETABLE_ROW_MAPPER = (rs, rowNum) ->
        new Timetable(rs.getLong("courseId"), rs.getInt("dayOfWeek"), rs.getTime("startTime"),
                rs.getTime("endTime"));

    @Autowired
    public TimetableDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("timetables");
    }

    @Override
    public boolean create(Course course, int dayOfWeek, Time start, Time end) {
        final Map<String,Object> args = new HashMap<>();
        args.put("courseId", course.getCourseId());
        args.put("dayOfWeek", dayOfWeek);
        args.put("startTime", start);
        args.put("endTime", end);

        final Number rowsAffected = jdbcInsert.execute(args);
        return  rowsAffected.intValue() > 0;
    }

    @Override
    public boolean update(Long course_id, int dayOfWeek, Time start, Time end) {
        return jdbcTemplate.update("UPDATE timetables " +
                "SET dayOfWeek = ?," +
                "startTime = ?," +
                "endTime = ?" +
                "WHERE courseId = ?", new Object[]{dayOfWeek,start,end,course_id}) == 1;
    }

    @Override
    public boolean delete(Long course_id) {
        return jdbcTemplate.update("DELETE FROM timetables WHERE courseId = ?", new Object[]{course_id}) == 1;
    }

    @Override
    public List<Timetable> getById(Long courseId) {
        return jdbcTemplate.query("SELECT * FROM timetables WHERE courseId = ?",
                new Object[]{courseId}, TIMETABLE_ROW_MAPPER);
    }
}
