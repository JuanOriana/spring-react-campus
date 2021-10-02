package ar.edu.itba.paw.models.exception;

public class DuplicateCourseException extends RuntimeException {
    private final String errorMessage;
    public DuplicateCourseException(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return ExceptionMessageUtil.translate(this.errorMessage);
    }
}
