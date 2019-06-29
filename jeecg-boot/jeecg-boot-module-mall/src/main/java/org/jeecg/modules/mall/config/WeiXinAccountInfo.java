package org.jeecg.modules.mall.config;

import lombok.Data;


/**
 * 
 * @author Humphrey
 * 
 */
@Data
public class WeiXinAccountInfo {
    private String id;
    private String mchId;
    private String appId;
    private String appSecret;
    private String apiSecret;
    private byte[] apiClientCert;
}
