package cn.anitano.sell.service.impl;

import cn.anitano.sell.dataobject.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: 杨11352
 * @Date: 2019/10/26 22:53
 */
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    ProductServiceImpl productService;
    @Test
    void findById() {
        ProductInfo productInfo = productService.findById("123456");
        System.out.println(productInfo);
        assertNotNull(productInfo);
    }

    @Test
    void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        assertNotEquals(0,productInfoList.size());
    }

    @Test
    void findAll() {
        PageRequest pageRequest=PageRequest.of(0,5);
        Page<ProductInfo> page = productService.findAll(pageRequest);
        System.out.println(page.getTotalElements());
    }

    @Test
    @Transactional
    void save() {
        ProductInfo productInfo= new ProductInfo();
        productInfo.setProductId("123458");
        productInfo.setProductName("花甲粉4");
        productInfo.setProductPrice(new BigDecimal("8.0"));
        productInfo.setProductStock(100);
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductDescription("花甲粉可以");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = productService.save(productInfo);
        assertNotNull(result);
    }
}