package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.RoleDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class RoleDaoJpa implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role create(String roleName) {
        Role role = new Role.Builder()
                .withRoleName(roleName)
                .build();
        em.persist(role);
        return role;
    }

    @Override
    public boolean update(Integer roleId, String roleName) {
        Optional<Role> dbRole = Optional.ofNullable(em.find(Role.class, roleId));
        if (!dbRole.isPresent()) return false;
        dbRole.get().setRoleName(roleName);
        return true;
    }

    @Override
    public boolean delete(Integer roleId) {
        Optional<Role> dbRole = Optional.ofNullable(em.find(Role.class, roleId));
        if (!dbRole.isPresent()) return false;
        em.remove(dbRole.get());
        return true;
    }

    @Override
    public List<Role> list() {
        TypedQuery<Role> roleTypedQuery = em.createQuery("SELECT role FROM Role role", Role.class);
        return roleTypedQuery.getResultList();
    }
}
