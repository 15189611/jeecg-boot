package org.jeecg.modules.crm.vo;

import java.util.List;
import org.jeecg.modules.crm.entity.Customer;
import org.jeecg.modules.crm.entity.Contacts;
import org.jeecg.modules.crm.entity.Trace;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CustomerPage {

	/**id*/
	private java.lang.String id;
	/**客户名称*/
  	@Excel(name = "客户名称", width = 15)
	private java.lang.String name;
	/**客户编号*/
  	@Excel(name = "客户编号", width = 15)
	private java.lang.String customerNo;
	/**所在地区*/
  	@Excel(name = "所在地区", width = 15)
	private java.lang.Integer areaId;
	/**客户来源*/
  	@Excel(name = "客户来源", width = 15)
	private java.lang.Integer source;
	/**经济类型*/
  	@Excel(name = "经济类型", width = 15)
	private java.lang.Integer ecotype;
	/**客户等级*/
  	@Excel(name = "客户等级", width = 15)
	private java.lang.Integer level;
	/**客户行业*/
  	@Excel(name = "客户行业", width = 15)
	private java.lang.Integer trade;
	/**满意度（1-5），默认为3*/
  	@Excel(name = "满意度（1-5），默认为3", width = 15)
	private java.lang.Integer satisfy;
	/**信用度（1-5），默认为3*/
  	@Excel(name = "信用度（1-5），默认为3", width = 15)
	private java.lang.Integer credit;
	/**联系地址*/
  	@Excel(name = "联系地址", width = 15)
	private java.lang.String address;
	/**网址*/
  	@Excel(name = "网址", width = 15)
	private java.lang.String website;
	/**邮编*/
  	@Excel(name = "邮编", width = 15)
	private java.lang.String zipcode;
	/**联系人*/
  	@Excel(name = "联系人", width = 15)
	private java.lang.String linkman;
	/**手机*/
  	@Excel(name = "手机", width = 15)
	private java.lang.String mobile;
	/**座机*/
  	@Excel(name = "座机", width = 15)
	private java.lang.String tel;
	/**传真*/
  	@Excel(name = "传真", width = 15)
	private java.lang.String fax;
	/**邮箱*/
  	@Excel(name = "邮箱", width = 15)
	private java.lang.String email;
	/**主营产品*/
  	@Excel(name = "主营产品", width = 15)
	private java.lang.String mainProduct;
	/**沟通情况*/
  	@Excel(name = "沟通情况", width = 15)
	private java.lang.String talk;
	/**status*/
  	@Excel(name = "status", width = 15)
	private java.lang.String status;
	/**介绍*/
  	@Excel(name = "介绍", width = 15)
	private java.lang.Object intro;
	/**创建人员*/
  	@Excel(name = "创建人员", width = 15)
	private java.lang.String createBy;
	/**归属人员*/
  	@Excel(name = "归属人员", width = 15)
	private java.lang.String ownerUserId;
	/**createTime*/
  	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**下次沟通时间*/
  	@Excel(name = "下次沟通时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date nextTime;
	/**最近联系时间*/
  	@Excel(name = "最近联系时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date connTime;
	/**最近沟通内容*/
  	@Excel(name = "最近沟通内容", width = 15)
	private java.lang.String connBody;
	/**updateBy*/
  	@Excel(name = "updateBy", width = 15)
	private java.lang.String updateBy;
	/**updateTime*/
  	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	@ExcelCollection(name="联系人")
	private List<Contacts> contactsList;
	@ExcelCollection(name="跟进信息")
	private List<Trace> traceList;
	
}
