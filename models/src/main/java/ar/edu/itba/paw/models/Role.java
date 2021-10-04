package ar.edu.itba.paw.models;

import java.util.Objects;

public class Role {
    private Integer roleId;
    private String roleName;

    public Role(Builder builder) {
        this.roleId = builder.roleId;
        this.roleName = builder.roleName;
    }

    public static class Builder {

        private Integer roleId;
        private String roleName;

        public Builder() {
        }
        public Builder withRoleId(int roleId){
            this.roleId= roleId;
            return this;
        }
        public Builder withRoleName(String roleName){
            this.roleName= roleName;
            return this;
        }

        public Role build() {
            if (this.roleId == null) {
                throw new NullPointerException("The property \"roleId\" is null. "
                        + "Please set the value by \"roleId()\". "
                        + "The properties \"roleId\" and \"roleName\" are required.");
            }
            if (this.roleName == null) {
                throw new NullPointerException("The property \"roleName\" is null. "
                        + "Please set the value by \"roleName()\". "
                        + "The properties \"roleId\" and \"roleName\" are required.");
            }
            return new Role(this);
        }
    }


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId.equals(role.roleId) && Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
    }
}
