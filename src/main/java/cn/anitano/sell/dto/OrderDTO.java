package cn.anitano.sell.dto;

import cn.anitano.sell.dataobject.OrderDetail;
import cn.anitano.sell.enums.OrderStatusEnum;
import cn.anitano.sell.enums.PayStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: OrderDTO
 * @Author: 杨11352
 * @Date: 2019/10/31 19:50
 */
@Data
@DynamicUpdate
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
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

    private List<OrderDetail> orderDetailList;
}
