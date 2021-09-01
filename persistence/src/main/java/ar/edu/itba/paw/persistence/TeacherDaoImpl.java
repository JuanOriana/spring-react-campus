
package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TeacherDao;
import ar.edu.itba.paw.models.Student;
import ar.edu.itba.paw.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class TeacherDaoImpl implements TeacherDao {
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Teacher> ROW_MAPPER = (rs, rowNum) -> new Teacher(rs.getLong("id"),rs.getString("name"),rs.getString("surname"),rs.getString("email"),rs.getString("username"),rs.getString("password"));



    @Autowired
    public TeacherDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("teachers");

    }
    @Override
    public Optional<Teacher> getById(long id) {
        return jdbcTemplate.query("SELECT * FROM teachers WHERE id = ?",new Object[]{id},ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<Teacher> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM teachers", ROW_MAPPER));
    }

    @Override
    public boolean create(Teacher teacher) {
        final Map<String,Object> args = new HashMap<>();
        args.put("id",teacher.getId());
        args.put("name",teacher.getName());
        args.put("surname",teacher.getSurname());
        args.put("mail",teacher.getEmail());
        args.put("username",teacher.getUsername());
        args.put("password",teacher.getPassword());

        final Number rowsAffected = jdbcInsert.execute(args);

        return  rowsAffected.intValue() > 0;
    }

    @Override
    public boolean update(int id, Teacher teacher) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }


}



