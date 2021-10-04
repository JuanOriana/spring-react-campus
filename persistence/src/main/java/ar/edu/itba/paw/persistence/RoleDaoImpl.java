package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.RoleDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public RoleDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles").usingGeneratedKeyColumns("roleId");
    }

    @Override
    public Role create(String roleName) {
        final Map<String, Object> args = new HashMap<>();
        args.put("roleName", roleName);
        final int roleId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new Role.Builder().withRoleId(roleId).withRoleName(roleName).build();
    }

    @Override
    public boolean update(Integer roleId, String roleName) {
        return jdbcTemplate.update("UPDATE roles " +
                "SET roleName = ?" +
                "WHERE roleId = ?", roleName, roleId) == 1;
    }

    @Override
    public boolean delete(Integer roleId) {
        return jdbcTemplate.update("DELETE FROM roles WHERE roleId = ?", roleId) == 1;
    }

    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) ->
            new Role.Builder().withRoleId(rs.getInt("roleid")).withRoleName(rs.getString("rolename")).build();


    @Override
    public List<Role> list() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM roles" , ROLE_ROW_MAPPER));
    }
}
