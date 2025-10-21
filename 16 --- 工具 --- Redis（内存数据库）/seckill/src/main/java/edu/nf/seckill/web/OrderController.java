package edu.nf.seckill.web;

import edu.nf.seckill.common.result.ResultVO;
import edu.nf.seckill.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangl
 * @date 2024/11/26
 */
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final SeckillService seckillService;

    @PostMapping("/order/{gid}")
    public ResultVO placeOrder(@PathVariable("gid") int gid) {
        seckillService.placeOrder(gid);
        return ResultVO.success();
    }
}