package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.models.Role;
import org.springframework.hateoas.Link;

import java.io.Serializable;
import java.util.List;

public class RoleDto implements Serializable {

    private Integer roleId;
    private String roleName;
    private List<Link> links;

    public RoleDto() {
        // For MessageBodyWriter
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

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
