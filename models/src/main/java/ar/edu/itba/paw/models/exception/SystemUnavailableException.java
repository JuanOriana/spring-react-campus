package ar.edu.itba.paw.models.exception;


public class SystemUnavailableException extends RuntimeException {
    private final String errorMessage;
    public SystemUnavailableException(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return ExceptionMessageUtil.translate(this.errorMessage);
    }
}
