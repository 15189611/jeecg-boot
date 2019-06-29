package org.jeecg.modules.mall.api.vo;

/**
 * 微信统一下单请求实体
 */
public class ReqUniformOrder {

    private String appid;
    private String mch_id;
    private String nonce_str;
    private String out_trade_no;
    private String body;
    private Integer total_fee;
    private String notify_url;
    private String trade_type;
    private String spbill_create_ip;
    private String openid;
    private String sign;


}
