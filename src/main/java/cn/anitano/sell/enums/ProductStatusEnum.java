package cn.anitano.sell.enums;

import lombok.Getter;

/**
 * @Author: 杨11352
 * @Date: 2019/10/26 22:40
 */
@Getter
public enum ProductStatusEnum {
    UP(0,"在架"),
    DOWN(0,"下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
