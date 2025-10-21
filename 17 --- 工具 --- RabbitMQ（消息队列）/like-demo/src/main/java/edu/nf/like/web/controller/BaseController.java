package edu.nf.like.web.controller;

import edu.nf.like.common.result.ResultData;
import org.springframework.http.HttpStatus;

/**
 * @author wangl
 * @date 2023/12/6
 */
public class BaseController {

    public <T> ResultData<T> success(T data) {
        ResultData<T> vo = new ResultData<>();
        vo.setCode(HttpStatus.OK.value());
        vo.setData(data);
        return vo;
    }

    public ResultData<?> success() {
        ResultData<?> vo = new ResultData<>();
        vo.setCode(HttpStatus.OK.value());
        return vo;
    }

    public ResultData<?> error(int code, String message) {
        ResultData<?> vo = new ResultData<>();
        vo.setCode(code);
        vo.setMessage(message);
        return vo;
    }
}