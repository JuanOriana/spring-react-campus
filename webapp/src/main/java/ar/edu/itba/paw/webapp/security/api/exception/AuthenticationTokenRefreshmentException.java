package ar.edu.itba.paw.webapp.security.api.exception;

public class AuthenticationTokenRefreshmentException extends RuntimeException {

    public AuthenticationTokenRefreshmentException(String message) {
        super(message);
    }

    public AuthenticationTokenRefreshmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
