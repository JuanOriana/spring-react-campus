package ar.edu.itba.paw.models.exception;


import ar.edu.itba.paw.models.ExceptionMessageUtil;

public class DuplicateUserException extends RuntimeException {
    private final String errorMessage;
    public DuplicateUserException(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return ExceptionMessageUtil.translate(this.errorMessage);
    }
}
