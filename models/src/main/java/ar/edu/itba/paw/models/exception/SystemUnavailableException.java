package ar.edu.itba.paw.models.exception;


import ar.edu.itba.paw.models.ExceptionMessageUtil;

public class SystemUnavailableException extends RuntimeException {
    private final String errorMessage;
    public SystemUnavailableException(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return ExceptionMessageUtil.translate(this.errorMessage);
    }
}
