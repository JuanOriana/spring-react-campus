package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class UserDaoJpa extends BasePaginationDaoImpl<User> implements UserDao {

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


    @Override
    public boolean update(Long userId, User user) {
        Optional<User> dbUser = findById(userId);
        if(!dbUser.isPresent()) return false;
        dbUser.get().merge(user);
        return true;
    }


    @Override
    public boolean delete(Long userId) {
        Optional<User> dbUser = findById(userId);
        if(!dbUser.isPresent()) return false;
        em.remove(dbUser.get());
        return true;
    }


    @Override
    public Optional<Role> getRole(Long userId, Long courseId) {
        TypedQuery<Role> dbRole = em.createQuery("SELECT e.role FROM Enrollment e WHERE e.course.courseId = :courseId AND e.user.userId = :userId", Role.class);
        dbRole.setParameter("courseId", courseId);
        dbRole.setParameter("userId", userId);
        List<Role> roles = dbRole.getResultList();
        return Optional.ofNullable(roles.isEmpty() ? null : roles.get(0));
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
    public CampusPage<User> filterByCourse(Long courseId, CampusPageRequest pageRequest) {
        Map<String, Object> properties = new HashMap<>();
        String query = "SELECT userId FROM users WHERE isAdmin = false AND userId NOT IN (SELECT userId FROM users NATURAL JOIN user_to_course WHERE courseId = :courseId) ORDER BY fileNumber";
        String mappingQuery = "SELECT user FROM User user WHERE user.userId IN (:ids) ORDER BY user.fileNumber";
        properties.put("courseId", courseId);
        return listBy(properties, query, mappingQuery, pageRequest, User.class);
    }

    @Override
    public CampusPage<User> list(CampusPageRequest pageRequest) {
        Map<String, Object> properties = new HashMap<>();
        String query = "SELECT userId FROM users ORDER BY filenumber";
        String mappingQuery = "SELECT u FROM User u WHERE u.userId IN (:ids) ORDER BY u.fileNumber";
        return listBy(properties, query, mappingQuery, pageRequest, User.class);
    }


    @Override
    public CampusPage<User> getStudentsByCourse(Long courseId, CampusPageRequest pageRequest) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("courseId", courseId);
        properties.put("roleId", Roles.STUDENT.getValue());
        String query = "SELECT userId FROM user_to_course NATURAL JOIN users WHERE courseId = :courseId AND roleId = :roleId ORDER BY filenumber";
        String mappingQuery = "SELECT u FROM User u WHERE u.userId IN (:ids) ORDER BY u.fileNumber";
        return listBy(properties, query, mappingQuery, pageRequest, User.class);
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
        return true;
    }

    @Override
    public Optional<User> findByFileNumber(Integer fileNumber) {
        final TypedQuery<User> query = em.createQuery("SELECT u from User u where u.fileNumber = :fileNumber",
                User.class);
        query.setParameter("fileNumber", fileNumber);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final TypedQuery<User> query = em.createQuery("SELECT u from User u where u.email = :email",
                User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }
    

    @Override
    public Integer getMaxFileNumber(){
        final TypedQuery<Integer> query = em.createQuery("SELECT u.fileNumber FROM User u WHERE u.fileNumber >= ALL (SELECT u2.fileNumber FROM User u2)",Integer.class);
        return query.getSingleResult();
    }
}
