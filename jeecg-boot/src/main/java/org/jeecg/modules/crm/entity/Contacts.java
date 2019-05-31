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
 * @Description: 联系人
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
@Data
@TableName("crm_contacts")
public class Contacts implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/**customerId*/
	private java.lang.String customerId;
	/**name*/
    @Excel(name = "name", width = 15)
	private java.lang.String name;
	/**姓别*/
    @Excel(name = "姓别", width = 15)
	private java.lang.Integer gender;
	/**职位、*/
    @Excel(name = "职位、", width = 15)
	private java.lang.String postion;
	/**tel*/
    @Excel(name = "tel", width = 15)
	private java.lang.String tel;
	/**mobile*/
    @Excel(name = "mobile", width = 15)
	private java.lang.String mobile;
	/**qicq*/
    @Excel(name = "qicq", width = 15)
	private java.lang.String qicq;
	/**email*/
    @Excel(name = "email", width = 15)
	private java.lang.String email;
	/**zipcode*/
    @Excel(name = "zipcode", width = 15)
	private java.lang.String zipcode;
	/**address*/
    @Excel(name = "address", width = 15)
	private java.lang.String address;
	/**intro*/
    @Excel(name = "intro", width = 15)
	private java.lang.Object intro;
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
