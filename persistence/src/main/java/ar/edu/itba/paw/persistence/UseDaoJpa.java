package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class UseDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(Integer fileNumber, String name, String surname, String username, String email, String password, boolean isAdmin) {
        return null;
    }

    @Override
    public boolean update(Long userId, User user) {
        return false;
    }

    @Override
    public boolean delete(Long userId) {
        return false;
    }

    @Override
    public Optional<Role> getRole(Long userId, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        
        return Optional.empty();
    }

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        return Optional.empty();
    }

    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        return false;
    }
}
