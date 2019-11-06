package cn.anitano.sell.service;

import cn.anitano.sell.dto.OrderDTO;

/**
 * @ClassName: BuyerService
 * @Author: 杨11352
 * @Date: 2019/11/4 20:58
 */
public interface BuyerService {
    /**查看订单详情*/
    OrderDTO findOrderOne(String openid,String orderId);
    OrderDTO cancelOrder(String openid,String orderId);
}
