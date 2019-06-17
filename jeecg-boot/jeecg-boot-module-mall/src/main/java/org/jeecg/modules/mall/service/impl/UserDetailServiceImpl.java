package org.jeecg.modules.mall.service.impl;

import org.jeecg.modules.mall.entity.UserDetail;
import org.jeecg.modules.mall.mapper.UserDetailMapper;
import org.jeecg.modules.mall.service.IUserDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 消息信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements IUserDetailService {
	
	@Autowired
	private UserDetailMapper userDetailMapper;
	
	@Override
	public List<UserDetail> selectByMainId(String mainId) {
		return userDetailMapper.selectByMainId(mainId);
	}
}
