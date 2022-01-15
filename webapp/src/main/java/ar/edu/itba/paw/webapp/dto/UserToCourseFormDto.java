package ar.edu.itba.paw.webapp.dto;

import javax.validation.constraints.NotNull;

public class UserToCourseFormDto {

    @NotNull
    private Long userId;

    @NotNull
    private Integer roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
