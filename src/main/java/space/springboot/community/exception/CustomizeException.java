package space.springboot.community.exception;

public class CustomizeException extends RuntimeException {

    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getResultMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
