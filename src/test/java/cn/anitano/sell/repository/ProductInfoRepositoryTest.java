package cn.anitano.sell.repository;

import cn.anitano.sell.dataobject.ProductCategory;
import cn.anitano.sell.dataobject.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: 杨11352
 * @Date: 2019/10/26 15:49
 */
@SpringBootTest
class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo= new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("鱼腩粥");
        productInfo.setProductPrice(new BigDecimal("8.0"));
        productInfo.setProductStock(100);
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductDescription("比银记鱼腩粥还好喝");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = repository.save(productInfo);
        assertNotNull(result);
    }
    @Test
    public void findByProductStatusTest(){
        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        assertNotEquals(0,productInfoList.size());
    }
}