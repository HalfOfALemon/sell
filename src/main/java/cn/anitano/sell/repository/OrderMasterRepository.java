package cn.anitano.sell.repository;

import cn.anitano.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:     杨11352
 * @Date:    2019/10/31 9:10
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String > {
    Page<OrderMaster> findByBuyerOpenid(String openId, Pageable pageable);

}
