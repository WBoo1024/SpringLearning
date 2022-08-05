package crud.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @program: NewJavaWebProject
 * @description:
 * @author: 壹零二四
 * @created: 2022/07/20 15:31
 */
@Data
@Component
public class Result {
    private  int code;
    private String msg;
    private long count;
    private Object data;

    public static Result ok(){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    public static Result failed(){
        Result result = new Result();
        result.setCode(1);
        result.setMsg("failed");
        return result;
    }
}
