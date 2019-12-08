package space.springboot.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("主题没有找到，再确认下你的地址");

    private String resultCode;

    private String resultMessage;

    CustomizeErrorCode(String s) {
    }

    @Override
    public String getResultCode() {
        return null;
    }

    @Override
    public String getResultMessage() {
        return null;
    }
}
