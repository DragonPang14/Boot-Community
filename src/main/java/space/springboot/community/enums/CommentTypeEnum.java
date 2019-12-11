package space.springboot.community.enums;

public enum CommentTypeEnum {
    QUESTION_TYPE(1),
    COMMENT_TYPE(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
