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

    /**
     * 直连数据库
     * @param goodsId
     * @return
     */
//    @GetMapping("/order/{goodsId}")
//    public ResultVO createOrder(@PathVariable("goodsId") int goodsId) {
//        orderService.placeOrder(goodsId);
//        return ResultVO.success();
//    }





    /**
     * 下单
     * @param goodsId
     * @return
     */
//    @GetMapping("/order/pro/{goodsId}/{userId}")
//    public ResultVO createOrderPro(@PathVariable("goodsId") int goodsId, @PathVariable("userId") int userId) {
//        orderService.placeOrderPro(goodsId, userId);
//        return ResultVO.success();
//    }

    /**
     * 支付成功
     * @param userId
     * @return
     */
//    @GetMapping("/order/pay/{goodsId}/{userId}")
//    public ResultVO payOrder(@PathVariable("goodsId") int goodsId, @PathVariable("userId") int userId) {
//        orderService.payOrder(goodsId, userId);
//        return ResultVO.success();
//    }

    /**
     * 取消支付
     * @param userId
     * @return
     */
//    @GetMapping("/order/cancel/{goodsId}/{userId}")
//    public ResultVO cancelOrder(@PathVariable("goodsId") int goodsId, @PathVariable("userId") int userId) {
//        orderService.cancelOrder(goodsId, userId);
//        return ResultVO.success();
//    }





    /**
     * 下单
     * @param goodsId
     * @param userId
     * @param num
     * @return
     */
//    @GetMapping("/order/proMax/{goodsId}/{userId}/{num}")
//    public ResultVO createOrderProMax(@PathVariable("goodsId") int goodsId,
//                                   @PathVariable("userId") int userId,
//                                   @PathVariable("num") int num) {
//        return ResultVO.success(orderService.placeOrderProMax(goodsId, userId, num));
//    }

    /**
     * 支付成功
     * @param orderId
     * @return
     */
//    @GetMapping("/order/pay/pro/{orderId}")
//    public ResultVO payOrderPro(@PathVariable("orderId") String orderId) {
//        orderService.payOrderPro(orderId);
//        return ResultVO.success();
//    }

    /**
     * 取消支付
     * @param orderId
     * @return
     */
//    @GetMapping("/order/cancel/pro/{orderId}")
//    public ResultVO cancelOrderPro(@PathVariable("orderId") String orderId) {
//        orderService.cancelOrderPro(orderId);
//        return ResultVO.success();
//    }





    /**
     * 下单
     * @param goodsId
     * @param userId
     * @param num
     * @return
     */
    @GetMapping("/order/proMaxUltra/{goodsId}/{userId}/{num}")
    public ResultVO createOrderProMaxUltra(@PathVariable("goodsId") int goodsId,
                                      @PathVariable("userId") int userId,
                                      @PathVariable("num") int num) {
        return ResultVO.success(orderService.placeOrderProMaxUltra(goodsId, userId, num));
    }

    /**
     * 支付成功
     * @param orderId
     * @return
     */
    @GetMapping("/order/pay/proMax/{orderId}")
    public ResultVO payOrderProMax(@PathVariable("orderId") String orderId) {
        orderService.payOrderProMax(orderId);
        return ResultVO.success();
    }

    /**
     * 取消支付
     * @param orderId
     * @return
     */
    @GetMapping("/order/cancel/proMax/{orderId}")
    public ResultVO cancelOrderProMax(@PathVariable("orderId") String orderId) {
        orderService.cancelOrderProMax(orderId);
        return ResultVO.success();
    }
}
