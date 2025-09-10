package edu.nf.demo.controller;

import edu.nf.demo.vo.ResultVO;
import org.springframework.http.HttpStatus;

// 基础控制器
public class BaseController {
    /**
     * 响应成功（有数据）
     * @param data
     * @return
     * @param <T>
     */
    protected  <T> ResultVO<T> success(T data) {
        ResultVO<T> vo = new ResultVO<>(); // 创建响应对象
        vo.setCode(HttpStatus.OK.value()); // 设置响应码
        vo.setData(data); // 设置响应数据
        return vo;
    }

    /**
     * 响应成功（无数据）
     * @return
     * @param <T>
     */
    protected <T> ResultVO<T> success() {
        ResultVO<T> vo = new ResultVO<>(); // 创建响应对象
        vo.setCode(HttpStatus.OK.value()); // 设置响应码
        return vo;
    }
}