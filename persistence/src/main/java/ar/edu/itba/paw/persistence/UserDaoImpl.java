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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertProfileImage;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
            new User.Builder()
                    .withUserId(rs.getLong("userId"))
                    .withFileNumber(rs.getInt("fileNumber"))
                    .withName(rs.getString("name"))
                    .withSurname(rs.getString("surname"))
                    .withUsername(rs.getString("username"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .isAdmin(rs.getBoolean("isAdmin"))
                    .build();

    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) ->
        new Role(rs.getInt("roleId"), rs.getString("roleName"));

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("userid");
        jdbcInsertProfileImage = new SimpleJdbcInsert(jdbcTemplate).withTableName("profile_images").usingGeneratedKeyColumns("userid");
    }

    @Override
    public User create(Integer fileNumber, String name, String surname, String username, String email, String password,
                       boolean isAdmin) {
        final Map<String, Object> args = new HashMap<>();
        args.put("fileNumber", fileNumber);
        args.put("name", name);
        args.put("surname", surname);
        args.put("username", username);
        args.put("email", email);
        args.put("password", password);
        args.put("isAdmin", isAdmin);
        final Long userId = jdbcInsert.executeAndReturnKey(args).longValue();

        final Map<String, Object> argsProfileImage = new HashMap<>();
        args.put("image", null);
        args.put("userId", userId);
        jdbcInsertProfileImage.execute(argsProfileImage);

        return new User.Builder()
                .withUserId(userId)
                .withFileNumber(fileNumber)
                .withName(name)
                .withSurname(surname)
                .withUsername(username)
                .withEmail(email)
                .withPassword(password)
                .isAdmin(isAdmin)
                .build();
    }

    @Override
    public boolean update(Long userId, User user) {
        return jdbcTemplate.update("UPDATE users " +
                "SET fileNumber = ?," +
                "name = ?," +
                "surname = ?," +
                "username = ?, " +
                "email = ?, " +
                "password = ? ," +
                "isAdmin = ? " +
                "WHERE userId = ?;", user.getFileNumber(), user.getName(), user.getSurname(),
                user.getUsername(), user.getEmail(), user.getPassword(), user.isAdmin(), userId) == 1;
    }

    @Override
    public boolean delete(Long userId) {
        return jdbcTemplate.update("DELETE FROM users WHERE userId = ?", userId) == 1;
    }

    @Override
    public Optional<Role> getRole(Long userId, Long courseId) {
        return jdbcTemplate.query("SELECT * FROM user_to_course NATURAL JOIN roles WHERE userId = ? AND courseId = ?",
                new Object[]{userId, courseId}, ROLE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return jdbcTemplate.query("SELECT * FROM users WHERE userId = ?",
                new Object[]{userId}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM users WHERE username = ?",
                new Object[]{username}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<User> list() {
        return jdbcTemplate.query("SELECT * FROM users", USER_ROW_MAPPER);
    }

    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        return jdbcTemplate.update("UPDATE profile_images " +
                "SET image = ?" +
                "WHERE userId = ?", image, userId) == 1;
    }

    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        return jdbcTemplate.query("SELECT image FROM profile_images WHERE userId = ?", new Object[]{userId}, (rs, rowNumber) -> rs.getBytes("image")).stream().findFirst();
    }
}
