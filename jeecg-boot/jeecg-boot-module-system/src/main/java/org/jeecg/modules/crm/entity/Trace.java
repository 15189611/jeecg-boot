package org.jeecg.modules.crm.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 跟进信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
@Data
@TableName("crm_trace")
public class Trace implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/**customerId*/
	private java.lang.String customerId;
	/**contactsId*/
    @Excel(name = "contactsId", width = 15)
	private java.lang.String contactsId;
	/**chanceId*/
    @Excel(name = "chanceId", width = 15)
	private java.lang.String chanceId;
	/**connTime*/
	@Excel(name = "connTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date connTime;
	/**沟通阶段*/
    @Excel(name = "沟通阶段", width = 15)
	private java.lang.Integer salestage;
	/**销售方式*/
    @Excel(name = "销售方式", width = 15)
	private java.lang.Integer salemode;
	/**主题名称*/
    @Excel(name = "主题名称", width = 15)
	private java.lang.String name;
	/**intro*/
    @Excel(name = "intro", width = 15)
	private java.lang.Object intro;
	/**status*/
    @Excel(name = "status", width = 15)
	private java.lang.Integer status;
	/**下次联系时间*/
	@Excel(name = "下次联系时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date nextTime;
	/**下次沟通主题*/
    @Excel(name = "下次沟通主题", width = 15)
	private java.lang.String nexttitle;
	/**createBy*/
    @Excel(name = "createBy", width = 15)
	private java.lang.String createBy;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**updateBy*/
    @Excel(name = "updateBy", width = 15)
	private java.lang.String updateBy;
	/**updateTime*/
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
}
