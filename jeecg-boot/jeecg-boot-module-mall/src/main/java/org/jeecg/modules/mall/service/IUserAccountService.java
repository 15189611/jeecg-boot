package org.jeecg.modules.mall.service;

import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.entity.UserDetail;
import org.jeecg.modules.mall.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 用户信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
public interface IUserAccountService extends IService<UserAccount> {

	/**
	 * 添加一对多
	 * 
	 */
	public String saveMain(UserAccount userAccount,List<UserAuth> userAuthList,List<UserDetail> userDetailList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(UserAccount userAccount,List<UserAuth> userAuthList,List<UserDetail> userDetailList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


	String userAuth(UserAccount userAccount,UserAuth userAuth,UserDetail userDetail) ;
	
}
