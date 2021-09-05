package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
    }

    @Override
    public User create(User user) {
        final Map<String, Object> args = new HashMap<>();
        return null;
    }

    @Override
    public boolean update(int fileNumber, User user) {
        return false;
    }

    @Override
    public boolean delete(int fileNumber) {
        return false;
    }

    @Override
    public Role getRole(int fileNumber, int courseId) {
        return null;
    }

    @Override
    public User getByFileNumber(int fileNumber) {
        return null;
    }
}
