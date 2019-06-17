package org.jeecg.modules.mall.mapper;

import java.util.List;

import org.jeecg.modules.mall.entity.UserAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 授权信息
 * @Author: jeecg-boot
 * @Date: 2019-06-16
 * @Version: V1.0
 */
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    boolean deleteByMainId(String mainId);

    List<UserAuth> selectByMainId(String mainId);

    UserAuth queryByOpenId(String openId);

}
