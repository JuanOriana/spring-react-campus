package ar.edu.itba.paw.models.exception;

public class OrderParamArgumentException extends RuntimeException {
    public OrderParamArgumentException() {
        super("Malformed query parameter");
    }
}
