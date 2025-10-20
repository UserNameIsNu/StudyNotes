package edu.nf.seckill.common.result;

import lombok.Data;

/**
 * @author wangl
 * @date 2024/11/26
 */
@Data
public class ResultVO {

    private Integer code;
    private String message;
    private Object data;

    public static <T> ResultVO success(T data) {
        ResultVO vo = new ResultVO();
        vo.setCode(200);
        vo.setData(data);
        return vo;
    }

    public static ResultVO success() {
        ResultVO vo = new ResultVO();
        vo.setCode(200);
        return vo;
    }

    public static ResultVO error(int code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}