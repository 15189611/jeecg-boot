package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class ReqRemoveCart {

    private String userId;
    private String cartId;

}
