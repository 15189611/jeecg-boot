package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class RespGetOrderPayInfoVO {
    private String id;
    private String orderNo;
    /**实际金额*/
    private Integer realAmount;
    /**支付金额*/
    private Integer payAmount;
}
