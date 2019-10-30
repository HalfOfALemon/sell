package cn.anitano.sell.service;

import cn.anitano.sell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 杨11352
 * @Date: 2019/10/26 16:14
 */
public interface ProductService {
    ProductInfo findById(String productId);
    List<ProductInfo> findUpAll();
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);

}
