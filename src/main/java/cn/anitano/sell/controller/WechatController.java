package cn.anitano.sell.controller;

import cn.anitano.sell.config.WechatAccountConfig;
import cn.anitano.sell.config.WechatMpConfig;
import cn.anitano.sell.enums.ResultEnum;
import cn.anitano.sell.exception.SellException;
import com.google.common.base.Utf8;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @ClassName: WechatController
 * @Author: 杨11352
 * @Date: 2019/11/6 21:35
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;
    @Autowired
    WechatAccountConfig wechatAccountConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl) throws UnsupportedEncodingException {
        //1.配置
        //2.调用方法
        String url = "http://halfofalemon.ngrok.wendal.cn/sell/wechat/userInfo";
        String redirectResult = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO,URLEncoder.encode(returnUrl, "UTF-8"));
        logger.info("[微信授权] 获取code redirectResult = {}",redirectResult);
        return "redirect:"+redirectResult;
    }
    @GetMapping("userInfo")
    private String userInfo(@RequestParam("code")String code,@RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            logger.error("[微信网页授权] 获取AccessToken错误 {}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        logger.info("[微信授权] 获取openId openId = {}",openId);
        return "redirect:"+returnUrl + "?openId="+openId;
    }
}
