package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class ReqProductListByCategory {

    private String category;
    private String orderType;
    private String sortType;

}
