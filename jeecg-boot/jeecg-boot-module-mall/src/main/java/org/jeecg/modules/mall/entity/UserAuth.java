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
 * @Description: 授权信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
@Data
@TableName("t_shop_user_auth")
public class UserAuth implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.UUID)
	private String id;
	/**用户ID*/
	private String userId;
	/**登录类型*/
	private Integer identityType;
	/**标识（第三方应用的唯一标识）*/
    @Excel(name = "标识（第三方应用的唯一标识）", width = 15)
	private String openId;
	/**密码凭证*/
    @Excel(name = "密码凭证", width = 15)
	private String credential;
}
