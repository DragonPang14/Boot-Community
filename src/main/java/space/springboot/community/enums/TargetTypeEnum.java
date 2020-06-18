package space.springboot.community.enums;

/**
 * @desc 评论类型枚举
 * @date
 */
public enum TargetTypeEnum {
    //问题评论
    QUESTION_TYPE(1,"文章"),
    //评论回复
    COMMENT_TYPE(2,"评论")
    ;
    private Integer type;
    private String remark;

    TargetTypeEnum(Integer type,String remark)
    {
        this.type = type;
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }
    public String getRemark() {
        return remark;
    }

    public static String getRemark(Integer type){
        for (TargetTypeEnum value : TargetTypeEnum.values()) {
            if(value.getType() == type){
                return value.getRemark();
            }
        }
        return null;
    }


}
