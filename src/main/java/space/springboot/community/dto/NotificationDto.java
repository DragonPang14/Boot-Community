package space.springboot.community.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Integer id;
    private Integer targetId;
    private String targetContent;   //通知目标对象内容
    private Integer targetType;   //通知目标对象类型
    private String targetTypeStr;   //通知目标对象类型描述
    private UserDto sender;
    private UserDto receiver;
    private String notiContent;   //通知消息内容（评论内容）
    private int action;
    private String actionStr;   //动作描述
    private int status;   //0未读  1已读
    private long gmtCreate;
}
