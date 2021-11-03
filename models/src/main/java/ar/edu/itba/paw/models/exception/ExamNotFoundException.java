package ar.edu.itba.paw.models.exception;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException() {
    }

    public ExamNotFoundException(String message) {
        super(message);
    }

    public ExamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExamNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExamNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
