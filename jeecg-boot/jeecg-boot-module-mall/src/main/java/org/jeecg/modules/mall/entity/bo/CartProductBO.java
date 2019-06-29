package org.jeecg.modules.mall.entity.bo;


import lombok.Data;

@Data
public class CartProductBO {

    private String id;
    private String userId;
    private String productId;
    private String productName;
    private Integer sellingPrice;
    private Integer num;
    private Integer status;
    private String picUrl;
}
