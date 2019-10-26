package cn.anitano.sell.service;

import cn.anitano.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findbyid(Integer categoryId);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);
}
