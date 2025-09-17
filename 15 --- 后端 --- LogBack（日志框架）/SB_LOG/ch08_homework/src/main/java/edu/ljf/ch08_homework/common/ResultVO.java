package edu.ljf.ch08_homework.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(200);
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> ResultVO<T> success() {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(200);
        return resultVO;
    }

    public static <T> ResultVO<T> error(int code, String message) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
