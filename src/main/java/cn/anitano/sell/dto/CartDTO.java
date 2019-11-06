package cn.anitano.sell.dto;

import lombok.Data;

/**
 * @ClassName: CartDTO
 * @Author: 杨11352
 * @Date: 2019/10/31 21:46
 */
@Data
public class CartDTO {
    /**商品id*/
    private String productId;
    /**商品数量*/
    private Integer productQuantity;

    public CartDTO() {
    }
    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
