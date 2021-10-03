package ar.edu.itba.paw.models.exception;


public class DuplicateUserException extends RuntimeException {
    private final String errorMessage;
    public DuplicateUserException(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return ExceptionMessageUtil.translate(this.errorMessage);
    }

    public boolean isUsernameDuplicated() {
        return ExceptionMessageUtil.getField(this.errorMessage).contains("username");
    }

    public boolean isFileNumberDuplicated() {
        return ExceptionMessageUtil.getField(this.errorMessage).contains("filenumber");
    }
    public boolean isEmailDuplicated() {
        return ExceptionMessageUtil.getField(this.errorMessage).contains("email");
    }
}
