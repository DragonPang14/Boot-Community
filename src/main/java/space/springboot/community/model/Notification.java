package space.springboot.community.model;

import lombok.Data;

/**
 * @desc 通知pojo
 */
@Data
public class Notification {
    private Integer id;
    private Integer targetId;
    private Integer targetType;
    private Integer senderId;
    private Integer receiveId;
    private String notiContent;
    private int action;
    private int status;
    private long gmtCreate;
    private int delFlag;

}
