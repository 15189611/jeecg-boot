package org.jeecg.modules.mall.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderInfoVO {

    private String id;
    private String orderNo;
    /**用户ID*/
    private String userId;
    private Integer totalNum;
    /**订单总金额*/
    private Integer totalAmount;
    /**优惠金额*/
    private Integer discountAmount;
    private int freight;
    /**实际金额*/
    private Integer realAmount;
    /**支付金额*/
    private Integer payAmount;
    /**收件人*/
    private String consignee;
    /**收件地址*/
    private String address;
    /**收件人电话*/
    private String mobile;
    /**remark*/
    private String remark;

    private String addressId;

    private List productList;
}
