package ar.edu.itba.paw.models.exception;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(){
        this.status = "error.401status";
        this.body = "error.401body";
        this.statusCode = 401;
    }
}