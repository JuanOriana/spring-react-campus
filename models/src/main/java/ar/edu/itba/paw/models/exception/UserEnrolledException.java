package ar.edu.itba.paw.models.exception;

public class UserEnrolledException extends RuntimeException {
    public UserEnrolledException() {
        super("User is already enrolled to this course");
    }
}
