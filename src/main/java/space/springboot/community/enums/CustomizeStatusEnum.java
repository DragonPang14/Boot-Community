package space.springboot.community.enums;

public enum CustomizeStatusEnum {

    SUCCESS_CODE(100,"请求成功"),

    UNLOGIN_CODE(1001,"用户未登录"),

    UNRECOGNIZED_USER(1002,"未找到该用户"),

    CODE_ERROR(600,"操作出现异常"),

    QUESTION_NOT_FOUND(2001,"主题没有找到，再确认下你的地址"),

    COMMENT_NOT_FOUND(2002,"回复的评论未找到");

    private Integer statusCode;
    private String msg;

    CustomizeStatusEnum(Integer code, String msg) {
        this.statusCode = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return statusCode;
    }

    public String getMsg(){
        return msg;
    }

}
