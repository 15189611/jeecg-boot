package org.jeecg.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "wechat")
public class WeChatConfig {

    private String appId;
    private String secret;
    private String mchId;
    private String notifyUrl;
    private String tradeType;
    private String paySignKey;
    private String uniformOrder;
}
