package org.jeecg.modules.mall.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 订单商品
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Data
@TableName("t_shop_order_product")
public class OrderProduct implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.UUID)
	private String id;
	/**用户ID*/
    @Excel(name = "用户ID", width = 15)
	private String orderId;
	/**商品ID*/
	private String productId;
	/**商品价格*/
    @Excel(name = "商品价格", width = 15)
	private Integer productPrice;
	/**够买数量*/
    @Excel(name = "够买数量", width = 15)
	private Integer num;
}
