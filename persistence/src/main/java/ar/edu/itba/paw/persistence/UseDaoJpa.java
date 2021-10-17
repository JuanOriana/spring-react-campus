package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class UseDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public User create(Integer fileNumber, String name, String surname, String username,
                       String email, String password, boolean isAdmin) {
        final User user = new User.Builder()
                .withFileNumber(fileNumber)
                .withName(name)
                .withSurname(surname)
                .withUsername(username)
                .withEmail(email)
                .withPassword(password)
                .isAdmin(isAdmin)
                .build();
        em.persist(user);
        return user;
    }

    @Transactional
    @Override
    public boolean update(Long userId, User user) {
        Optional<User> dbUser = findById(userId);
        if(!dbUser.isPresent()) return false;
        dbUser.get().merge(user);
        em.flush();
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long userId) {
        Optional<User> dbUser = findById(userId);
        if(!dbUser.isPresent()) return false;
        em.remove(dbUser.get());
        return true;
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
        final TypedQuery<User> query = em.createQuery("SELECT u from User u where u.username = :username",
                User.class);
        query.setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<User> list() {
        TypedQuery<User> listUsers = em.createQuery("SELECT u from User u", User.class);
        return listUsers.getResultList();
    }

    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        Optional<User> user = findById(userId);
        return user.map(User::getImage);
    }

    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        Optional<User> user = findById(userId);
        if(!user.isPresent()) return false;
        user.get().setImage(image);
        em.flush();
        return true;
    }
}
