package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class OrderConfirmVO {

    private String remark;
    private String userId;
    private Integer  payPrice;
    private String  orderId;
}
