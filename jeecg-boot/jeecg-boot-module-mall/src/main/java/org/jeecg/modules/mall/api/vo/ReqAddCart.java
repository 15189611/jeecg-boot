package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class ReqAddCart {

    private String userId;
    private String productId;
    private Integer num;
}
