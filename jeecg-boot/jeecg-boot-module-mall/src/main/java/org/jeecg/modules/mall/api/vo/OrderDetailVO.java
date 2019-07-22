package org.jeecg.modules.mall.api.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailVO {
    private String id;
    private String orderNo;
    /**实际金额*/
    private Integer realAmount;
    /**支付金额*/
    private Integer payAmount;
    private Integer totalAmount;

    private String consignee;
    private String mobile;
    private String address;
    private String status;
    private List orderProduct;
    private Date createTime;
    private String remark;
}
