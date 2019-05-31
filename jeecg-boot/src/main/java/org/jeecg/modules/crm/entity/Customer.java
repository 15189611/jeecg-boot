package org.jeecg.modules.crm.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 客户信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
@Data
@TableName("crm_customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/**客户名称*/
	private java.lang.String name;
	/**客户编号*/
	private java.lang.String customerNo;
	/**所在地区*/
	private java.lang.Integer areaId;
	/**客户来源*/
	@Dict(dicCode = "channel")
	private Integer source;
	/**经济类型*/
	private java.lang.Integer ecotype;
	/**客户等级*/
	private java.lang.Integer level;
	/**客户行业*/
	private java.lang.Integer trade;
	/**满意度（1-5），默认为3*/
	private java.lang.Integer satisfy;
	/**信用度（1-5），默认为3*/
	private java.lang.Integer credit;
	/**联系地址*/
	private java.lang.String address;
	/**网址*/
	private java.lang.String website;
	/**邮编*/
	private java.lang.String zipcode;
	/**联系人*/
	private java.lang.String linkman;
	/**手机*/
	private java.lang.String mobile;
	/**座机*/
	private java.lang.String tel;
	/**传真*/
	private java.lang.String fax;
	/**邮箱*/
	private java.lang.String email;
	/**主营产品*/
	private java.lang.String mainProduct;
	/**沟通情况*/
	private java.lang.String talk;
	/**status*/
	private java.lang.String status;
	/**介绍*/
	private java.lang.Object intro;
	/**创建人员*/
	private java.lang.String createBy;
	/**归属人员*/
	private java.lang.String ownerUserId;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**下次沟通时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date nextTime;
	/**最近联系时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date connTime;
	/**最近沟通内容*/
	private java.lang.String connBody;
	/**updateBy*/
	private java.lang.String updateBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
}
