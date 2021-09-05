package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

public interface UserDao {
    User create(User user);
    boolean update(int fileNumber, User user);
    boolean delete(int fileNumber);
    Role getRole(int fileNumber, int courseId);
    User getByFileNumber(int fileNumber);
}
