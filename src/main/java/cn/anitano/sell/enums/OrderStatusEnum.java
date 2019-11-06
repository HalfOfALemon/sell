package cn.anitano.sell.enums;

import lombok.Getter;

/**
 * Author:     杨11352
 * Date:    2019/10/30 21:57
 */
@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"已完结"),
    CANCEL(2,"已取消")
    ;
    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
