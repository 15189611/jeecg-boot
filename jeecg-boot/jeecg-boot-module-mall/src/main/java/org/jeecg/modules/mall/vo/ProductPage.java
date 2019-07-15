package org.jeecg.modules.mall.vo;

import lombok.Data;
import org.jeecg.modules.mall.entity.Collection;
import org.jeecg.modules.mall.entity.ProductDetail;
import org.jeecg.modules.support.entity.Image;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Data
public class ProductPage {

	/**ID*/
	private String id;
	/**描述*/
  	@Excel(name = "商品标题", width = 15)
	private String title;
	/**商品标题*/
  	@Excel(name = "描述", width = 15)
	private String description;
	/**折扣价*/
  	@Excel(name = "折扣价", width = 15)
	private Integer discountPrice;
	/**categoryId*/
  	@Excel(name = "categoryId", width = 15)
	private Integer categoryId;
	/**销售价*/
  	@Excel(name = "销售价", width = 15)
	private Integer sellingPrice;
	/**成本价*/
  	@Excel(name = "成本价", width = 15)
	private Integer costPrice;
	/**是否推荐:0-否,1-是*/
  	@Excel(name = "是否推荐:0-否,1-是", width = 15)
	private Integer isRecommend;
	/**是否热销:0-否,1-是*/
  	@Excel(name = "是否热销:0-否,1-是", width = 15)
	private Integer isHot;
	/**是否新品:0-否,1-是*/
  	@Excel(name = "是否新品:0-否,1-是", width = 15)
	private Integer isNew;
	/**是否免邮:0-否,1-是*/
  	@Excel(name = "是否免邮:0-否,1-是", width = 15)
	private Integer isFree;
	/**库存*/
  	@Excel(name = "库存", width = 15)
	private Integer inventory;
	/**销量*/
  	@Excel(name = "销量", width = 15)
	private Integer sells;
	/**状态:1-上架,0-下架*/
  	@Excel(name = "状态:1-上架,0-下架", width = 15)
	private Integer status;

	@ExcelCollection(name="图片")
	private List<Image> imageList;
	@ExcelCollection(name="收藏信息")
	private List<Collection> collectionList;
	@ExcelCollection(name="商品详细信息")
	private List<ProductDetail> productDetailList;
	
}
