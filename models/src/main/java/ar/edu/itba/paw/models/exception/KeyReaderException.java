package ar.edu.itba.paw.models.exception;

public class KeyReaderException extends RuntimeException {
    public KeyReaderException() {
    }

    public KeyReaderException(String message) {
        super(message);
    }

    public KeyReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyReaderException(Throwable cause) {
        super(cause);
    }

    public KeyReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
