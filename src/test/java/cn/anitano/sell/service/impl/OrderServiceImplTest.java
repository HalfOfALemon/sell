package cn.anitano.sell.service.impl;

import cn.anitano.sell.dataobject.OrderDetail;
import cn.anitano.sell.dto.OrderDTO;
import cn.anitano.sell.enums.OrderStatusEnum;
import cn.anitano.sell.enums.PayStatusEnum;
import cn.anitano.sell.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName: OrderServiceImplTest
 * @Author: 杨11352
 * @Date: 2019/11/1 19:40
 */
@SpringBootTest
class OrderServiceImplTest {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    OrderServiceImpl orderService;
    private final String BUYEROPENID="1008611";
    private final String ORDERID="157261277816694507";
    @Test
    void create() {
        OrderDTO orderDTO =new OrderDTO();
        orderDTO.setBuyerName("杨先生的鱼腩粥和奶茶");
        orderDTO.setBuyerPhone("12345678910");
        orderDTO.setBuyerAddress("广州市天河区");
        orderDTO.setBuyerOpenid(BUYEROPENID);
        /*购物车详情*/
        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("123458");
        orderDetail2.setProductQuantity(2);
        orderDetailList.add(orderDetail2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        logger.info("result:{}",result);

    }

    @Test
    void findByOrderId() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDERID);
        logger.info("根据订单号查询订单:{}",orderDTO);
        Assertions.assertEquals(ORDERID,orderDTO.getOrderId());
    }

    @Test
    void getList() {
        PageRequest pageRequest = PageRequest.of(0,5);
        Page<OrderDTO> result = orderService.getList(BUYEROPENID, pageRequest);
        Assertions.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDERID);
        OrderDTO orderDTO_result = orderService.cancel(orderDTO);
        Assertions.assertEquals(OrderStatusEnum.CANCEL.getCode(),orderDTO_result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDERID);
        OrderDTO result = orderService.finish(orderDTO);
        Assertions.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    void pay() {
        OrderDTO orderDTO = orderService.findByOrderId(ORDERID);
        OrderDTO result = orderService.pay(orderDTO);
        Assertions.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}