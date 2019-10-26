package cn.anitano.sell.service.impl;

import cn.anitano.sell.dataobject.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: 11352
 * @Date: 2019/10/23 8:52
 */
@SpringBootTest
class CategoryServiceImplTest {
    @Autowired
    CategoryServiceImpl categoryService;
    @Test
    void findbyid() {
        ProductCategory productCategory = categoryService.findbyid(1);
        Assertions.assertEquals(1,productCategory.getCategoryId());
    }

    @Test
    void getAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assertions.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assertions.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void save() {
        ProductCategory productCategory=new ProductCategory("男生专享",6);
        ProductCategory result = categoryService.save(productCategory);
        assertNotNull(result);
    }
}