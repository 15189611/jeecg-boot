package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class ReqPrepay {
    private String userId;
    private String openId;
    private String orderId;
    private String appId;
}
