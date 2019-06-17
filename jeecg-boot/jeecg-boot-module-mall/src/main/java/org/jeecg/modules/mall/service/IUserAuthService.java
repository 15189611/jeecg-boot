package org.jeecg.modules.mall.service;

import org.jeecg.modules.mall.entity.UserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 授权信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
public interface IUserAuthService extends IService<UserAuth> {

	public List<UserAuth> selectByMainId(String mainId);

	public UserAuth queryByOpenId(String openId);
}
