package org.jeecg.modules.mall.service;

import org.jeecg.modules.mall.entity.UserDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 消息信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
public interface IUserDetailService extends IService<UserDetail> {

	public List<UserDetail> selectByMainId(String mainId);
}
