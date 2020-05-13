package space.springboot.community.model;

import lombok.Data;

/**
 * @desc 通知pojo
 */
@Data
public class Notification {
    private Integer id;
    private Integer targetId;
    private String notiContent;
    private Integer senderId;
    private int type;
    private int status;
    private int delFlag;

}
