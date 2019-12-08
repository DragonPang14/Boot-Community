package space.springboot.community.exception;

public class CustomizeException extends RuntimeException {

    private String code;

    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getResultCode();
        this.message = errorCode.getResultMessage();
    }



    @Override
    public String getMessage() {
        return message;
    }
}
