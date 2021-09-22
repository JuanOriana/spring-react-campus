package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.RoleDao;
import ar.edu.itba.paw.interfaces.RoleService;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role create(String roleName) {
        return roleDao.create(roleName);
    }

    @Override
    public boolean update(Integer roleId, String roleName) {
        return roleDao.update(roleId, roleName);
    }

    @Override
    public boolean delete(Integer roleId) {
        return roleDao.delete(roleId);
    }

    @Override
    public List<Role> list() {
        return roleDao.list();
    }
}
