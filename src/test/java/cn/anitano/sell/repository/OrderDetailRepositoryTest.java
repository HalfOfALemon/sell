package cn.anitano.sell.repository;

import cn.anitano.sell.dataobject.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName: OrderDetailRepositoryTest
 * @Author: 杨11352
 * @Date: 2019/10/31 16:58
 */
@SpringBootTest
class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("12345679");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123457");
        orderDetail.setProductName("花甲粉");
        orderDetail.setProductPrice(new BigDecimal("8.0"));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://xxxxxx.jpg");

        OrderDetail result = repository.save(orderDetail);
        Assertions.assertNotNull(result);
    }
    @Test
    public void findByOrderId(){
        List<OrderDetail> result = repository.findByOrderId("1234567");
        Assertions.assertNotEquals(0,result.size());
    }
}