package cn.anitano.sell.repository;

import cn.anitano.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: OrderDetailRepository
 * @Author: 杨11352
 * @Date: 2019/10/31 15:55
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findByOrderId(String orderId);
}
