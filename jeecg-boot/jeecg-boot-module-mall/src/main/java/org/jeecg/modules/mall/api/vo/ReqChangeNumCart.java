package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class ReqChangeNumCart {

    private String userId;
    private String cartId;
    private String num;
    private String action;

}
