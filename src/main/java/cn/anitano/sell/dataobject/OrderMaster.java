package cn.anitano.sell.dataobject;

import cn.anitano.sell.enums.OrderStatusEnum;
import cn.anitano.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:     杨11352
 * @Date:    2019/10/30 21:38
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    /**订单id*/
    @Id
    private String orderId;
    /**买家名字*/
    private String buyerName;
    /**买家电话*/
    private String buyerPhone;
    /**买家地址*/
    private String buyerAddress;

    /**买家微信openid*/
    private String buyerOpenid;

    /**订单总金额*/
    private BigDecimal orderAmount;

    /**订单状态,默认0新下订单*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**支付状态,默认0未支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;

}
