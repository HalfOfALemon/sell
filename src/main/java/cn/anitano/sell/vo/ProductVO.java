package cn.anitano.sell.vo;

import cn.anitano.sell.dataobject.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *商品包含类目
 * @Author: 杨11352
 * @Date: 2019/10/27 14:44
 */
@Data
public class ProductVO {
    /** 类目名称*/
    @JsonProperty("name")
    private String categoryName;
    /** 类目编号*/
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
