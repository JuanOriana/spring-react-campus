package ar.edu.itba.paw.models;

public enum Roles {
    STUDENT(1),
    TEACHER(2),
    ASSISTANT(3);
    private final Integer roleId;
    Roles(int roleId) {
        this.roleId = roleId;
    }
    public Integer getValue() {
        return roleId;
    }
}
