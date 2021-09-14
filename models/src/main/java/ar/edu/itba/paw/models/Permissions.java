package ar.edu.itba.paw.models;

public enum Permissions { STUDENT(1), HELPER(2), TEACHER(3);
    private final int id;
    Permissions(int id) {this.id = id;}
    public int getValue() { return id; }
};