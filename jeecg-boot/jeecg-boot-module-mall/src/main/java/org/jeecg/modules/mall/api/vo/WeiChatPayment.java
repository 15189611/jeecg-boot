package org.jeecg.modules.mall.api.vo;

import lombok.Data;

/**
 * 调起微信支付的参数（后台返回给小程序）
 */
@Data
public class WeiChatPayment {
    private String timeStamp;
    private String nonceStr;
    private String packages ;
    private String signType;
    private String paySign;
}
