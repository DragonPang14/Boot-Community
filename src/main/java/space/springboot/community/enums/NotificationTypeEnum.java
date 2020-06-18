package space.springboot.community.enums;

public enum NotificationTypeEnum {

    LIKE_ACTION(1,"点赞了"),

    COMMENT_ACTION(2,"评论了")

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

    public static String getRemark(int code){
        for (NotificationTypeEnum value : NotificationTypeEnum.values()) {
            if(value.getCode() == code){
                return value.getRemark();
            }
        }
        return null;
    }

}
