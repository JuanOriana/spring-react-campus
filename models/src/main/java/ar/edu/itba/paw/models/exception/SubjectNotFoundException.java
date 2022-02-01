package ar.edu.itba.paw.models.exception;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException() {
        super("The subject does not exist");
    }

    public SubjectNotFoundException(String message) {
        super(message);
    }

    public SubjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public SubjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
