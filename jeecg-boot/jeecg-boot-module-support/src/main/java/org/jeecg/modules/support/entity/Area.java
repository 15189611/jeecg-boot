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
 * @Description: 区域信息
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
@Data
@TableName("t_area")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="t_area对象", description="区域信息")
public class Area {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private String id;
	/**province*/
	@Excel(name = "province", width = 15)
    @ApiModelProperty(value = "province")
	private String province;
	/**provinceName*/
	@Excel(name = "provinceName", width = 15)
    @ApiModelProperty(value = "provinceName")
	private String provinceName;
	/**city*/
	@Excel(name = "city", width = 15)
    @ApiModelProperty(value = "city")
	private String city;
	/**cityName*/
	@Excel(name = "cityName", width = 15)
    @ApiModelProperty(value = "cityName")
	private String cityName;
	/**area*/
	@Excel(name = "area", width = 15)
    @ApiModelProperty(value = "area")
	private String area;
	/**areaName*/
	@Excel(name = "areaName", width = 15)
    @ApiModelProperty(value = "areaName")
	private String areaName;
	/**父级代码*/
	@Excel(name = "父级代码", width = 15)
    @ApiModelProperty(value = "父级代码")
	private String parentCode;
	/**type*/
	@Excel(name = "type", width = 15)
    @ApiModelProperty(value = "type")
	private Integer type;
}
