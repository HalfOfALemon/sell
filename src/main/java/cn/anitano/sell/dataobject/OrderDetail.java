package cn.anitano.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Author:     杨11352
 * Date:    2019/10/30 22:13
 */
@Entity
@Data
public class OrderDetail {
    /**详情表id*/
    @Id
    private String detailId;
    /**订单id*/
    private String orderId;
    /**商品id*/
    private String productId;
    /**商品名称*/
    private String productName;

    /**商品单价格*/
    private BigDecimal productPrice;
    /**商品数量*/
    private Integer productQuantity;
    /**商品小图*/
    private String productIcon;
}
