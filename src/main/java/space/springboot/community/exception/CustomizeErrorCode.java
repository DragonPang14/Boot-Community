package space.springboot.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"主题没有找到，再确认下你的地址");

    private Integer code;
    private String Message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        Message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return Message;
    }
}
