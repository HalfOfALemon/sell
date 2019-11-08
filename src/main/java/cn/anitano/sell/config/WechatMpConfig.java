package cn.anitano.sell.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName: WechatMpConfig
 * @Author: 杨11352
 * @Date: 2019/11/6 21:48
 */
@Component
public class WechatMpConfig {
    @Autowired
    WechatAccountConfig wechatAccountConfig;
    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpServiceBean=new WxMpServiceImpl();
        wxMpServiceBean.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpServiceBean;
    }
    @Bean
    public WxMpDefaultConfigImpl wxMpConfigStorage(){
        WxMpDefaultConfigImpl wxMpDefaultConfigBean=new WxMpDefaultConfigImpl();
        wxMpDefaultConfigBean.setAppId(wechatAccountConfig.getMpAppId());
        wxMpDefaultConfigBean.setSecret(wechatAccountConfig.getMpAppSecret());
        return wxMpDefaultConfigBean;
    }
}
