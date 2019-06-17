package org.jeecg.modules.mall.service.impl;

import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.mapper.UserAuthMapper;
import org.jeecg.modules.mall.service.IUserAuthService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 授权信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements IUserAuthService {
	
	@Autowired
	private UserAuthMapper userAuthMapper;
	
	@Override
	public List<UserAuth> selectByMainId(String mainId) {
		return userAuthMapper.selectByMainId(mainId);
	}

	@Override
	public UserAuth queryByOpenId(String openId) {
		return userAuthMapper.queryByOpenId(openId);
	}
}
