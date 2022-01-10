package ar.edu.itba.paw.webapp.auth.exceptions;


import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialException extends AuthenticationException {
    public InvalidCredentialException(String msg) {
        super(msg);
    }
}
