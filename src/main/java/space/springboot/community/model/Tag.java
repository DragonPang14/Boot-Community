package space.springboot.community.model;

import lombok.Data;

@Data
public class Tag {

    private Integer id;
    private String tagName;
    private String remarks;
    private Long gmtCreate;
    private Long gmtModify;
}
