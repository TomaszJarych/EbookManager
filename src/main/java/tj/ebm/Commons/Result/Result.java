package tj.ebm.Commons.Result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Result {

    private String code;
    private String message;
    private String error;
    private Object data;

    private Result(String code) {
        this.code = code;
    }

    public static Result ok(Object data) {
        Result result = new Result("200");
        result.setMessage("OK");
        result.setData(data);
        return result;
    }

    public static Result ok(String message) {
        Result result = new Result("200");
        result.setMessage(message);
        return result;
    }

    public static Result error(String error) {
        Result result = new Result("500");
        result.setMessage("ERROR");
        result.setError(error);
        return result;
    }

    public static Result error(String error, Object data) {
        Result result = new Result("500");
        result.setMessage("ERROR");
        result.setError(error);
        result.setData(data);
        return result;
    }
}
