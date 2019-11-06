package cn.anitano.sell.service.impl;

import cn.anitano.sell.dto.OrderDTO;
import cn.anitano.sell.enums.ResultEnum;
import cn.anitano.sell.exception.SellException;
import cn.anitano.sell.service.BuyerService;
import cn.anitano.sell.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: BuyerServiceImpl
 * @Author: 杨11352
 * @Date: 2019/11/4 21:01
 */
@Service
public class BuyerServiceImpl implements BuyerService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return this.checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = this.checkOrderOwner(openid, orderId);
        if(orderDTO == null){
            logger.error("[取消订单] 查询不到该订单 orderId={}",orderId);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        if(orderDTO == null){
            return null;
        }
        //判断是不是自己的订单
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            logger.error("[查询订单] 订单的 openid 不一致，openid ={},orderId ={}",openid,orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

}
