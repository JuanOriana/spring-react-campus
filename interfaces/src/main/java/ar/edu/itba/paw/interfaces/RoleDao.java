package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;

import java.util.List;

public interface RoleDao {
    Role create(String roleName);
    boolean update(Integer roleId, String roleName);
    boolean delete(Integer roleId);
    List<Role> list();
}
