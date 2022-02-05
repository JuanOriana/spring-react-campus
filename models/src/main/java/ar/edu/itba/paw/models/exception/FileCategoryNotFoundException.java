package ar.edu.itba.paw.models.exception;

public class FileCategoryNotFoundException extends RuntimeException {
    public FileCategoryNotFoundException() {
        super("The file category does not exist");
    }

    public FileCategoryNotFoundException(String message) {
        super(message);
    }

    public FileCategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileCategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public FileCategoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
