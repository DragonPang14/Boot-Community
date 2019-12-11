package space.springboot.community.dto;

import lombok.Data;

@Data
public class ResultDto {

    private Integer code;
    private String msg;
    private Object obj;
}
