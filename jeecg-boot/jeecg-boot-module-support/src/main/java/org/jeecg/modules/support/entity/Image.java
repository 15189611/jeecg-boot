package org.jeecg.modules.support.entity;

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
 * @Description: 图片管理
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Data
@TableName("t_image")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="t_image对象", description="图片管理")
public class Image {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**业务ID*/
	@Excel(name = "业务ID", width = 15)
    @ApiModelProperty(value = "业务ID")
	private java.lang.String businessId;
	/**业务类型: 1/banner图；2/商品*/
	@Excel(name = "业务类型: 1/banner图；2/商品", width = 15)
    @ApiModelProperty(value = "业务类型: 1/banner图；2/商品")
	private java.lang.Integer businessType;
	/**图片类型: 1/主图,2/详细图*/
	@Excel(name = "图片类型: 1/主图,2/详细图,3缩略图", width = 15)
    @ApiModelProperty(value = "图片类型: 1/主图,2/详细图")
	private java.lang.Integer type;
	/**图片路径*/
	@Excel(name = "图片路径", width = 15)
    @ApiModelProperty(value = "图片路径")
	private java.lang.String url;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
	private java.lang.Integer sort;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private java.lang.String remark;
	/**状态: 1/有效,2/删除*/
	@Excel(name = "状态: 1/有效,2/删除", width = 15)
    @ApiModelProperty(value = "状态: 1/有效,2/删除")
	private java.lang.Integer status;
}
