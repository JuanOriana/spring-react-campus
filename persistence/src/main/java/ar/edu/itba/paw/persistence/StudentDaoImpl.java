package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Student;
import ar.edu.itba.paw.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.*;

public class StudentDaoImpl implements StudentDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Student> ROW_MAPPER = (rs, rowNum) -> new Student(rs.getLong("id"),rs.getString("name"),rs.getString("surname"),rs.getString("email"),rs.getString("username"),rs.getString("password"));

    @Autowired
    public StudentDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("students");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  students ( "+
                "id LONG PRIMARY KEY, "+
                "name varchar(50), "+
                "surname varchar (50), "+
                "email varchar (50), "+
                "username varchar(50), "+
                "password varchar (50) )");

    }
    @Override
    public Optional<Student> getById(long id) {
        return jdbcTemplate.query("SELECT * FROM students WHERE id = ?",new Object[]{id},ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<Student> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM students", ROW_MAPPER));
    }

    @Override
    public Student create(Student student) {
        final Map<String,Object> args = new HashMap<>();
        args.put("name",student.getName());
        args.put("surname",student.getSurname());
        args.put("mail",student.getEmail());
        args.put("username",student.getUsername());
        args.put("password",student.getPassword());

        final int studentId = jdbcInsert.execute(args);

        return new Student(studentId, student.getName(), student.getSurname(), student.getEmail(), student.getUsername(),
                student.getPassword());
    }

    @Override
    public boolean update(int id, Student student) {
        return jdbcTemplate.update("UPDATE students " +
                "SET name = ?," +
                "surname = ?," +
                "email = ?," +
                "username = ?," +
                "password = ?," +
                "WHERE id = ?", new Object[]{student.getName(),student.getSurname(),student.getEmail(),student.getUsername(),student.getPassword(), id}) == 1;

    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM students WHERE id = ?", new Object[]{id}) == 1;
    }
}
