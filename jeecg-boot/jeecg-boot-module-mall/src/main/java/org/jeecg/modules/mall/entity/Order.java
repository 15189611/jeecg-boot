package org.jeecg.modules.mall.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 订单信息
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Data
@TableName("t_shop_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type = IdType.UUID)
	private String id;
	/**用户ID*/
	private String userId;
	private String orderNo;
	/**交易流水*/
	private String tradeNo;
	/**orderTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date orderTime;

	//订单商品数量
	private Integer totalNum;
	/**订单总金额*/
	private Integer totalAmount;
	/**优惠金额*/
	private Integer discountAmount;
	/**实际金额*/
	private Integer realAmount;
	/**支付金额*/
	private Integer payAmount;
	/**运费*/
	private Double freight;
	/**支付状态*/
	private Integer payStatus;
	/**收件人*/
	private String consignee;
	/**收件地址*/
	private String address;
	/**收件人电话*/
	private String mobile;
	/**订单状态：0-创建*/
	private Integer status;
	/**remark*/
	private String remark;


	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**修改人*/

	@ApiModelProperty(value = "修改人")
	private java.lang.String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "修改时间")
	private java.util.Date updateTime;

}
