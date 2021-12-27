package cn.wolfcode.trip.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JsonResult {
    private int code;
    private String msg;
    private Object data;

    public JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult success(){
        return new JsonResult(200,null,null);
    }
    public static JsonResult success(Object data){
        return new JsonResult(200,null,data);
    }
    public static JsonResult error(String msg) {
        return new JsonResult(500, null, msg);
    }

    public static JsonResult error(int code, String msg) {
        return new JsonResult(code, null, msg);
    }


}
