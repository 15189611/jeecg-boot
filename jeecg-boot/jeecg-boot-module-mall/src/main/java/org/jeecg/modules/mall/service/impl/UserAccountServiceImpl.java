package org.jeecg.modules.mall.service.impl;

import org.jeecg.modules.mall.entity.UserAccount;
import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.entity.UserDetail;
import org.jeecg.modules.mall.mapper.UserAuthMapper;
import org.jeecg.modules.mall.mapper.UserDetailMapper;
import org.jeecg.modules.mall.mapper.UserAccountMapper;
import org.jeecg.modules.mall.service.IUserAccountService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 用户信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

	@Autowired
	private UserAccountMapper userAccountMapper;
	@Autowired
	private UserAuthMapper userAuthMapper;
	@Autowired
	private UserDetailMapper userDetailMapper;
	
	@Override
	@Transactional
	public String saveMain(UserAccount userAccount, List<UserAuth> userAuthList,List<UserDetail> userDetailList) {
		userAccountMapper.insert(userAccount);
		for(UserAuth entity:userAuthList) {
			//外键设置
			entity.setUserId(userAccount.getId());
			userAuthMapper.insert(entity);
		}
		for(UserDetail entity:userDetailList) {
			//外键设置
			entity.setUserId(userAccount.getId());
			userDetailMapper.insert(entity);
		}

		return userAccount.getId();
	}

	@Override
	@Transactional
	public void updateMain(UserAccount userAccount,List<UserAuth> userAuthList,List<UserDetail> userDetailList) {
		userAccountMapper.updateById(userAccount);
		
		//1.先删除子表数据
		userAuthMapper.deleteByMainId(userAccount.getId());
		userDetailMapper.deleteByMainId(userAccount.getId());
		
		//2.子表数据重新插入
		for(UserAuth entity:userAuthList) {
			//外键设置
			entity.setUserId(userAccount.getId());
			userAuthMapper.insert(entity);
		}
		for(UserDetail entity:userDetailList) {
			//外键设置
			entity.setUserId(userAccount.getId());
			userDetailMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		userAuthMapper.deleteByMainId(id);
		userDetailMapper.deleteByMainId(id);
		userAccountMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			userAuthMapper.deleteByMainId(id.toString());
			userDetailMapper.deleteByMainId(id.toString());
			userAccountMapper.deleteById(id);
		}
	}

	@Override
	public String userAuth(UserAccount userAccount, UserAuth userAuth, UserDetail userDetail) {
		userAccountMapper.insert(userAccount);
		//外键设置
		if(userAuth!=null) {
			userAuth.setUserId(userAccount.getId());
			userAuthMapper.insert(userAuth);
		}

		userDetail.setUserId(userAccount.getId());
		userDetailMapper.insert(userDetail);

		return userAccount.getId();
	}
}
