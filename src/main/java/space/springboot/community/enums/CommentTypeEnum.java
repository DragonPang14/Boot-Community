package space.springboot.community.enums;

/**
 * @desc 评论类型枚举
 * @date
 */
public enum CommentTypeEnum {
    //问题评论
    QUESTION_TYPE(1),
    //评论回复
    COMMENT_TYPE(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type){
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(value.getType() == type){
                return true;
            }
        }
        return false;
    }
}
