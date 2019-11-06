package cn.anitano.sell.repository;

import cn.anitano.sell.dataobject.OrderMaster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;


/**
 * @ClassName: OrderMasterRepositoryTest
 * @Author: 杨11352
 * @Date: 2019/10/31 16:13
 */
@SpringBootTest
class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;
    private final String OPENID="1008611";
    @Test
    public void saveTest() {
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("杨先生2");
        orderMaster.setBuyerPhone("12345678912");
        orderMaster.setBuyerAddress("广州天河");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal("2.8"));

        OrderMaster result = repository.save(orderMaster);
        Assertions.assertNotNull(result);
    }
    @Test
    public void findByBuyerOpenid(){
        PageRequest pageRequest=PageRequest.of(0,5);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, pageRequest);
        System.out.println(result.getTotalElements());
    }

}