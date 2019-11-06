package cn.anitano.sell.exception;

import cn.anitano.sell.enums.ResultEnum;

/**
 * @ClassName: SellException
 * @Author: 杨11352
 * @Date: 2019/10/31 20:39
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public SellException(Integer code,String message){
        super(message);
        this.code=code;
    }
}
