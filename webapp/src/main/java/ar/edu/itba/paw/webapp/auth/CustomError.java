package ar.edu.itba.paw.webapp.auth;

import org.springframework.http.HttpStatus;

public class CustomError {
    private final String error;
    private final HttpStatus status;

    public CustomError(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
