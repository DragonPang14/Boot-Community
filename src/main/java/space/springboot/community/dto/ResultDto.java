package space.springboot.community.dto;

import lombok.Data;
import space.springboot.community.enums.CustomizeStatusEnum;

@Data
public class ResultDto<T> {

    private Integer code;
    private String msg;
    private T obj;

    public ResultDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDto(CustomizeStatusEnum statusEnum){
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
    }

    public ResultDto(){

    }

    public static <T> ResultDto okOf(T t) {
        ResultDto resultDto = new ResultDto(200,"success!");
        resultDto.setObj(t);
        return resultDto;
    }
}
