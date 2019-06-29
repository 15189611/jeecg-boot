package org.jeecg.modules.mall.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 我的收藏
 * @Author: jeecg-boot
 * @Date:   2019-06-27
 * @Version: V1.0
 */
@Data
@TableName("t_shop_collection")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="t_shop_collection对象", description="我的收藏")
public class Collection {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**userId*/
	@Excel(name = "userId", width = 15)
    @ApiModelProperty(value = "userId")
	private java.lang.String userId;
	/**productId*/
	@Excel(name = "productId", width = 15)
    @ApiModelProperty(value = "productId")
	private java.lang.String productId;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
}
