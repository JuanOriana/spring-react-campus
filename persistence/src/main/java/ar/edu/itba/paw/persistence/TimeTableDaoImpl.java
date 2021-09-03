package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TimeTableDao;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TimeTableDaoImpl implements TimeTableDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    //private static final RowMapper<Teacher> ROW_MAPPER;

    @Autowired
    public TimeTableDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("timetables");
    }

    @Override
    public boolean create(Course course, int dayOfWeek, long start, long duration) {
        if (!isValidTimeAndDay(dayOfWeek,start)){
            return false;
        }
        final Map<String,Object> args = new HashMap<>();
        args.put("courseId", course.getCourseId());
        args.put("dayOfWeek", dayOfWeek);
        args.put("beginning", start);
        args.put("duration", duration);

        final Number rowsAffected = jdbcInsert.execute(args);
        return  rowsAffected.intValue() > 0;
    }

    @Override
    public boolean update(int course_id, int dayOfWeek, long start, long duration) {
        if (!isValidTimeAndDay(dayOfWeek,start)){
            return false;
        }
        return jdbcTemplate.update("UPDATE timetables " +
                "SET dayOfWeek = ?," +
                "beginning = ?," +
                "duration = ?" +
                "WHERE courseId = ?", new Object[]{dayOfWeek,start,duration,course_id}) == 1;
    }

    @Override
    public boolean delete(int course_id) {
        return jdbcTemplate.update("DELETE FROM timetables WHERE courseId = ?", new Object[]{course_id}) == 1;
    }

    @Override
    public Optional<Integer> getDayOfWeekOfCourseById(long course_id) {
        return Optional.of(jdbcTemplate.queryForObject("SELECT dayOfWeek FROM timetables WHERE courseId = ?",new Object[]{course_id}, Integer.class));
    }

    @Override
    public Optional<Long> getStartOfCourseById(long course_id) {
        return Optional.of(jdbcTemplate.queryForObject("SELECT beginning FROM timetables WHERE courseId = ?",new Object[]{course_id}, Long.class));
    }

    @Override
    public Optional<Long> getDurationOfCourseById(long course_id) {
        return Optional.of(jdbcTemplate.queryForObject("SELECT duration FROM timetables WHERE courseId = ?",new Object[]{course_id}, Long.class));
    }

    private boolean isValidTimeAndDay(int dayOfWeek, long start){
        return dayOfWeek <= 7 && dayOfWeek >= 1 && start < 86400000;
    }
}
