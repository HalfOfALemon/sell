package cn.anitano.sell.repository;

import cn.anitano.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 杨11352
 * @Date: 2019/10/26 15:38
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /**
     * 根据ID找到商品状态
     * @param productStatus 产品状态
     * @return 商品list
     */
    List<ProductInfo> findByProductStatus (Integer productStatus);
}
