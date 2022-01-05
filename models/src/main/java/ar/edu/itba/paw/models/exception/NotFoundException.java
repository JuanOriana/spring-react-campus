package ar.edu.itba.paw.models.exception;

public class NotFoundException extends ApiException {
    public NotFoundException() {
        this.status = "error.404status";
        this.body = "error.404body";
        this.statusCode = 404;
    }
}
