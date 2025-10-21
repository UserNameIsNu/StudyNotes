package edu.nf.seckill.mapper;

import edu.nf.seckill.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangl
 * @date 2024/11/26
 */
public interface OrderMapper {

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    Order getOrderById(int id);

    /**
     * 生成订单
     * @param order
     */
    void createOrder(Order order);

    /**
     * 修改订单状态
     * @param id
     * @param status
     */
    void updateStatus(@Param("id") int id,
                      @Param("status") int status);
}