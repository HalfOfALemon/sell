package cn.anitano.sell.enums;

import lombok.Getter;

/**
 * Author:     杨11352
 * Date:    2019/10/30 22:04
 */
@Getter
public enum PayStatusEnum {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功")
    ;
    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
