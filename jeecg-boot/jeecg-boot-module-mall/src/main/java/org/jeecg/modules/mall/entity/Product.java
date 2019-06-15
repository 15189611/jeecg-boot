package org.jeecg.modules.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

import java.io.Serializable;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Data
@TableName("t_shop_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type = IdType.UUID)
	private String id;
	/**描述*/
	private String title;
	/**商品标题*/
	private String description;
	/**折扣价*/
	private Integer discountPrice;
	/**categoryId*/
	private Integer categoryId;
	/**销售价*/
	private Integer sellingPrice;
	/**成本价*/
	private Integer costPrice;
	/**是否推荐:0-否,1-是*/
	private Integer isRecommand;
	/**是否热销:0-否,1-是*/
	private Integer isHot;
	/**是否新品:0-否,1-是*/
	private Integer isNew;
	/**是否免邮:0-否,1-是*/
	private Integer isFree;
	/**重量*/
	private Double weight;
	/**尺寸*/
	private Double volume;
	/**尺寸单位*/
	private String volType;
	/**库存*/
	private Integer inventory;
	/**销量*/
	private Integer sells;
	/**状态:1-上架,0-下架*/
	@Dict(dicCode = "product_status")
	private Integer status;
}
