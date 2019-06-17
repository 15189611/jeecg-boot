package org.jeecg.modules.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: Banner图
 * @Author: jeecg-boot
 * @Date:   2019-06-01
 * @Version: V1.0
 */
@Data
@TableName("t_shop_banner")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="t_shop_banner对象", description="Banner图")
public class Banner {
    
	/**ID*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "ID")
	private String id;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private java.lang.String description;
	/**排序*/
    @ApiModelProperty(value = "排序")
	private java.lang.String sort;
	/**相关业务ID*/
    @ApiModelProperty(value = "相关业务ID")
	private java.lang.String businessId;
	/**图片ID*/
    @ApiModelProperty(value = "图片ID")
	private java.lang.String picUrl;
	/**类型：1-banner图；2 推荐视频*/
    @ApiModelProperty(value = "类型：1-banner图；2-推荐视频")
	private java.lang.Integer type;
	/**status*/
    @ApiModelProperty(value = "status")
	private java.lang.Integer status;
}
