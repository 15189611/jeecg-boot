package org.jeecg.modules.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.mall.entity.Collection;

import java.util.List;

/**
 * @Description: 收藏信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
public interface CollectionMapper extends BaseMapper<Collection> {

	public boolean deleteByMainId(String mainId);
    
	public List<Collection> selectByMainId(String mainId);
}
