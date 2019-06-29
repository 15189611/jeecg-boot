package org.jeecg.modules.mall.entity.bo;

import lombok.Data;

@Data
public class OrderProductBO {
    private String id;
    /**用户ID*/
    private String orderId;
    /**商品ID*/
    private String productId;
    private String title;
    /**商品价格*/
    private Integer productPrice;
    private String picUrl;
    /**够买数量*/
    private Integer num;
}
