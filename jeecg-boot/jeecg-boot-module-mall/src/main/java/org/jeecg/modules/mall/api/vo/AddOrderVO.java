package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class AddOrderVO {

    private String userId;
    private String productId;
    private String action;  //direct_buy：直接购买
    private Integer amount;  //直接购买的数量
    private String addressId;
    private String orderId;
}
