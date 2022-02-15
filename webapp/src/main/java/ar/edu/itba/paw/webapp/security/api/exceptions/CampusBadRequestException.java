package ar.edu.itba.paw.webapp.security.api.exceptions;

public class CampusBadRequestException extends RuntimeException {
    public CampusBadRequestException(String message) {
        super(message);
    }
}
