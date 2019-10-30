package cn.anitano.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 杨11352
 * @Date: 2019/10/27 14:49
 */
@Data
public class ProductInfoVO {
    @JsonProperty("id")
    private String productId;
    /** 名字 */
    @JsonProperty("name")
    private String productName;

    /** 单价 */
    @JsonProperty("price")
    private BigDecimal productPrice;
    /** 描述 */
    @JsonProperty("description")
    private String productDescription;

    /** 商品小图 */
    @JsonProperty("icon")
    private String productIcon;
}
