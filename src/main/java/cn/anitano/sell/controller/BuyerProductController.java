package cn.anitano.sell.controller;

import cn.anitano.sell.dataobject.ProductCategory;
import cn.anitano.sell.dataobject.ProductInfo;
import cn.anitano.sell.service.CategoryService;
import cn.anitano.sell.service.ProductService;
import cn.anitano.sell.utils.ResultVOUtil;
import cn.anitano.sell.vo.ProductInfoVO;
import cn.anitano.sell.vo.ProductVO;
import cn.anitano.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 杨11352
 * @Date: 2019/10/27 11:23
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @RequestMapping("list")
    public ResultVO list() {
        /*1.查询所有在架商品*/
        List<ProductInfo> productInfoList= productService.findUpAll();
        /*2.一次查询所有类目*/
        /*传统的方式
        List<Integer> categoryTypeList=new ArrayList<>();
        for(ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }
        */
        /*lamda表达式*/
        List<Integer> categoryTypeList=productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        /*3.封装数据  resultVO  -> productVOList -> productInfoVOList*/
        List<ProductVO> productVOList =new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
