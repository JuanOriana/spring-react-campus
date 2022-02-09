package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Role;
import org.springframework.hateoas.ResourceSupport;

public class RoleDto extends ResourceSupport {

    private Integer roleId;
    private String roleName;

    public static RoleDto fromRole(Role role) {
        if (role == null){
            return null;
        }

        RoleDto dto = new RoleDto();
        dto.roleId = role.getRoleId();
        dto.roleName = role.getRoleName();
        return dto;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
