package ar.edu.itba.paw.models.exception;

public class FileExtensionNotFoundException extends RuntimeException {
    public FileExtensionNotFoundException() {
        super("The file extension does not exist");
    }

    public FileExtensionNotFoundException(String message) {
        super(message);
    }

    public FileExtensionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileExtensionNotFoundException(Throwable cause) {
        super(cause);
    }

    public FileExtensionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}