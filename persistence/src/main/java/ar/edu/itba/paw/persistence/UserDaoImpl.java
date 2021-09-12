package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        return new User(rs.getInt("userId"), rs.getInt("fileNumber"), rs.getString("name"),
                rs.getString("surname"), rs.getString("username"), rs.getString("email"),
                rs.getString("password"), rs.getBoolean("isAdmin"));
    };

    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) -> {
        return new Role(rs.getInt("roleId"), rs.getString("roleName"));
    };

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("userid");
    }

    @Override
    public User create(User user) {
        final Map<String, Object> args = new HashMap<>();
        args.put("fileNumber", user.getFileNumber());
        args.put("name", user.getName());
        args.put("surname", user.getSurname());
        args.put("username", user.getUsername());
        args.put("email", user.getEmail());
        args.put("password", user.getPassword());
        args.put("isAdmin", user.isAdmin());
        final int userId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new User(userId, user.getFileNumber(), user.getName(), user.getSurname(), user.getUsername(),
                user.getEmail(), user.getPassword(), user.isAdmin());
    }

    @Override
    public boolean update(int userId, User user) {
        return jdbcTemplate.update("UPDATE users " +
                "SET fileNumber = ?," +
                "name = ?," +
                "surname = ?," +
                "username = ?, " +
                "email = ?, " +
                "password = ? ," +
                "isAdmin = ? " +
                "WHERE userId = ?;", new Object[]{user.getFileNumber(), user.getName(), user.getSurname(),
                    user.getUsername(), user.getEmail(), user.getPassword(), user.isAdmin(), userId}) == 1;
    }

    @Override
    public boolean delete(int userId) {
        return jdbcTemplate.update("DELETE FROM users WHERE userId = ?", new Object[]{userId}) == 1;
    }

    @Override
    public Role getRole(int userId, int courseId) {
        return jdbcTemplate.query("SELECT * FROM user_to_course NATURAL JOIN roles WHERE userId = ? AND courseId = ?",
                new Object[]{userId, courseId}, ROLE_ROW_MAPPER).get(0);
    }

    @Override
    public Optional<User> findById(int userId) {
        return jdbcTemplate.query("SELECT * FROM users WHERE userId = ?",
                new Object[]{userId}, USER_ROW_MAPPER).stream().findFirst();
    }
}
