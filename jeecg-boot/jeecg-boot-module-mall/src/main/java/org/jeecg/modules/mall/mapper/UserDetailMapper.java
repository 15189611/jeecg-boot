package org.jeecg.modules.mall.mapper;

import java.util.List;
import org.jeecg.modules.mall.entity.UserDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 消息信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
public interface UserDetailMapper extends BaseMapper<UserDetail> {

	public boolean deleteByMainId(String mainId);
    
	public List<UserDetail> selectByMainId(String mainId);
}
