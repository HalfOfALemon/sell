package cn.anitano.sell.service;

import cn.anitano.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @ClassName: OrderService
 * @Author: 杨11352
 * @Date: 2019/10/31 19:40
 */
public interface OrderService {
    /**创建订单*/
    OrderDTO create(OrderDTO orderDTO);

    /**查询单个订单*/
    OrderDTO findByOrderId(String orderId);

    /**查询订单列表*/
    Page getList(String buyerOpenId, Pageable pageable);

    /**取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);

    /**完结订单*/
    OrderDTO finish(OrderDTO orderDTO);

    /**支付订单*/
    OrderDTO pay(OrderDTO orderDTO);
}
