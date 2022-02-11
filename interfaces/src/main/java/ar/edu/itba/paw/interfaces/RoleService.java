package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;

import java.util.List;

public interface RoleService {
    /**
     * Attempts to persist a Role entry in the database
     * @param roleName of the role to be created
     * @return instance of the Role that was created
     */
    Role create(String roleName);

    /**
     * Attempts to update a Role entry in the database
     * @param roleId of the role to be updated
     * @param roleName new name to be persisted
     * @return true if the update was successful , false otherwise
     */
    boolean update(Integer roleId, String roleName);

    /**
     * Attempts to delete a Role entry in the database
     * @param roleId of the role to be deleted
     * @return true if the delete was successful , false otherwise
     */
    boolean delete(Integer roleId);

    /**
     * Lists all roles available
     * @return list of Role's (if any)
     */
    List<Role> list();
}
