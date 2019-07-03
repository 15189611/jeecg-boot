package org.jeecg.modules.mall.vo;

import java.util.List;
import org.jeecg.modules.mall.entity.Order;
import org.jeecg.modules.mall.entity.OrderProduct;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 订单信息
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Data
public class OrderPage {

	/**ID*/
	private java.lang.String id;
	/**用户ID*/
  	@Excel(name = "用户ID", width = 15)
	private java.lang.String userId;
	/**交易流水*/
  	@Excel(name = "交易流水", width = 15)
	private java.lang.String tradeNo;
	/**orderTime*/
  	@Excel(name = "orderTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date orderTime;
	/**订单总金额*/
  	@Excel(name = "订单总金额", width = 15)
	private java.lang.Double totalAmount;
	/**优惠金额*/
  	@Excel(name = "优惠金额", width = 15)
	private java.lang.Double discountAmount;
	/**实际金额*/
  	@Excel(name = "实际金额", width = 15)
	private java.lang.Double realAmount;
	/**支付金额*/
  	@Excel(name = "支付金额", width = 15)
	private java.lang.Double payAmount;
	/**运费*/
  	@Excel(name = "运费", width = 15)
	private java.lang.Double freight;
	/**支付状态*/
  	@Excel(name = "支付状态", width = 15)
	private java.lang.String payStatus;
	/**收件人*/
  	@Excel(name = "收件人", width = 15)
	private java.lang.String consignee;
	/**收件地址*/
  	@Excel(name = "收件地址", width = 15)
	private java.lang.String address;
	/**收件人电话*/
  	@Excel(name = "收件人电话", width = 15)
	private java.lang.String mobile;
	/**订单状态：0-创建*/
  	@Excel(name = "订单状态：0-创建", width = 15)
	private java.lang.Integer status;
	/**remark*/
  	@Excel(name = "remark", width = 15)
	private java.lang.String remark;

	@ExcelCollection(name="订单商品")
	private List<OrderProduct> orderProductList;
	
}
