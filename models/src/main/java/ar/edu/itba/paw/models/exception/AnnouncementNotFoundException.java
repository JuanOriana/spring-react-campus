package ar.edu.itba.paw.models.exception;

public class AnnouncementNotFoundException extends RuntimeException {
    public AnnouncementNotFoundException() {
        super("The announcement does not exist");
    }

    public AnnouncementNotFoundException(String message) {
        super(message);
    }

    public AnnouncementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnouncementNotFoundException(Throwable cause) {
        super(cause);
    }

    public AnnouncementNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
