package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.RoleDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@Repository
public class RoleDaoJpa implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role create(String roleName) {
        return null;
    }

    @Override
    public boolean update(Integer roleId, String roleName) {
        return false;
    }

    @Override
    public boolean delete(Integer roleId) {
        return false;
    }

    @Override
    public List<Role> list() {
        return null;
    }
}
