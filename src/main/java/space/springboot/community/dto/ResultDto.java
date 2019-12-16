package space.springboot.community.dto;

import lombok.Data;

@Data
public class ResultDto {

    private Integer code;
    private String msg;
    private Object obj;

    public ResultDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDto() {

    }
}
