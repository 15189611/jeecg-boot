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
 * @Description: 收获地址
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Data
@TableName("t_shop_address")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="t_shop_address对象", description="收获地址")
public class Address {
    
	/**ID*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "ID")
	private String id;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
	private String userId;
	/**默认收获地址: 0/非默认,1/默认*/
	@Excel(name = "默认收获地址: 0/非默认,1/默认", width = 15)
    @ApiModelProperty(value = "默认收获地址: 0/非默认,1/默认")
	private Integer isDefault;
	/**收货人*/
	@Excel(name = "收货人", width = 15)
    @ApiModelProperty(value = "收货人")
	private String consignee;
	/**收货人手机*/
	@Excel(name = "收货人手机", width = 15)
    @ApiModelProperty(value = "收货人手机")
	private String mobile;
	/**邮编*/
	@Excel(name = "邮编", width = 15)
    @ApiModelProperty(value = "邮编")
	private String zipCode;
	/**省份*/
	@Excel(name = "省份", width = 15)
    @ApiModelProperty(value = "省份")
	private String provinceName;
	/**地市*/
	@Excel(name = "地市", width = 15)
    @ApiModelProperty(value = "地市")
	private String cityName;
	/**县区*/
	@Excel(name = "县区", width = 15)
    @ApiModelProperty(value = "县区")
	private String districtName;
	private String area;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
	private String address;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
    @ApiModelProperty(value = "修改人")
	private String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private java.util.Date updateTime;
}
