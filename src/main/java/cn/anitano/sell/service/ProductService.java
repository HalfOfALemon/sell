package cn.anitano.sell.service;

import cn.anitano.sell.dataobject.ProductInfo;
import cn.anitano.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: 杨11352
 * @Date: 2019/10/26 16:14
 */
public interface ProductService {
    ProductInfo findById(String productId);
    /**查询所有在架商品*/
    List<ProductInfo> findUpAll();
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);
    /**加库存*/
    void increaseStock(List<CartDTO> cartDTOList);
    /**减库存*/
    void decreaseStock(List<CartDTO> cartDTOList);

}
