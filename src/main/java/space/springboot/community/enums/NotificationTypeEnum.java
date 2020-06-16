package space.springboot.community.enums;

public enum NotificationTypeEnum {

    LIKE_ACTION(1,"点赞"),

    COMMENT_ACTION(2,"评论")

    ;


    private int code;
    private String remark;

    NotificationTypeEnum(int code, String remark){
        this.code = code;
        this.remark = remark;
    }

    public int getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}
