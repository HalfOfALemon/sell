package cn.anitano.sell.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: WeixinController
 * @Author: 杨11352
 * @Date: 2019/11/7 23:42
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping("/auth")
    public void auth(@RequestParam("code")String code){
        logger.info("[测试微信授权] code = {}",code);
    }
}
