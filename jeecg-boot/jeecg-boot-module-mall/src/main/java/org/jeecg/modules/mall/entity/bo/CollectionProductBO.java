package org.jeecg.modules.mall.entity.bo;

import lombok.Data;

@Data
public class CollectionProductBO {
    private String id;
    private String productId;
    private String title;
    private Integer productPrice;
    private String picUrl;
}
