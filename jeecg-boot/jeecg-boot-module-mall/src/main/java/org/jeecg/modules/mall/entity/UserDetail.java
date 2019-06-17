package org.jeecg.modules.mall.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 消息信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
@Data
@TableName("t_shop_user_detail")
public class UserDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.UUID)
	private String id;
	/**用户ID*/
	private String userId;
	/**性别 0/男,1/女*/
    @Excel(name = "性别 0/男,1/女", width = 15)
	private Integer gender;
	/**姓名*/
    @Excel(name = "姓名", width = 15)
	private String name;
	/**手机*/
    @Excel(name = "手机", width = 15)
	private String mobile;
	/**邮箱*/
    @Excel(name = "邮箱", width = 15)
	private String email;
	/**生日*/
	@Excel(name = "生日", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date birthdate;
	/**身份证号*/
    @Excel(name = "身份证号", width = 15)
	private String idCard;
	/**地址*/
    @Excel(name = "地址", width = 15)
	private String address;
}
