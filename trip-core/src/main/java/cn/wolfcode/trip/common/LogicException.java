package cn.wolfcode.trip.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LogicException extends RuntimeException{
    private int code;//异常错误码
    private String msg; // 异常错误信息
    public LogicException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
