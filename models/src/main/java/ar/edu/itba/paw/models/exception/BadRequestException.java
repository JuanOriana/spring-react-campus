package ar.edu.itba.paw.models.exception;

public class BadRequestException extends ApiException {
    public BadRequestException(){
        this.status = "error.400status";
        this.body = "error.400body";
        this.statusCode = 400;
    }
}
