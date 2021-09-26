package ar.edu.itba.paw.services.exception;


public class UserException extends RuntimeException {
    private final String errorMessage;
    public UserException(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
