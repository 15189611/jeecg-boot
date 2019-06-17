package org.jeecg.modules.mall.vo;

import java.util.List;
import org.jeecg.modules.mall.entity.UserAccount;
import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.entity.UserDetail;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 用户信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
@Data
public class UserAccountPage {

	/**id*/
	private java.lang.String id;
	/**用户名*/
  	@Excel(name = "用户名", width = 15)
	private java.lang.String userName;
	/**昵称*/
  	@Excel(name = "昵称", width = 15)
	private java.lang.String nickName;
	/**头像*/
  	@Excel(name = "头像", width = 15)
	private java.lang.String avatar;
	/**是否锁定: 0/锁定,1/正常*/
  	@Excel(name = "是否锁定: 0/锁定,1/正常", width = 15)
	private java.lang.Integer locked;
	/**最后登录时间*/
  	@Excel(name = "最后登录时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date lastVisitTime;
	/**注册时间*/
  	@Excel(name = "注册时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date registerTime;

	@ExcelCollection(name="授权信息")
	private List<UserAuth> userAuthList;
	@ExcelCollection(name="消息信息")
	private List<UserDetail> userDetailList;
	
}
