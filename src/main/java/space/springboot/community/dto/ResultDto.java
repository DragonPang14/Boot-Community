package space.springboot.community.dto;

import lombok.Data;

@Data
public class ResultDto<T> {

    private Integer code;
    private String msg;
    private T obj;

    public ResultDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDto(){

    }

    public static <T> ResultDto okOf(T t) {
        ResultDto resultDto = new ResultDto(200,"success!");
        resultDto.setObj(t);
        return resultDto;
    }
}
