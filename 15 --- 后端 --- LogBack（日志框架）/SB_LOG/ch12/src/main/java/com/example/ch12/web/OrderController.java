package com.example.ch12.web;

import com.example.ch12.common.result.ResultVO;
import com.example.ch12.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order/{goodsId}")
    public ResultVO createOrder(@PathVariable("goodsId") int goodsId) {
        orderService.placeOrder(goodsId);
        return ResultVO.success();
    }
}
