package org.jeecg.modules.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mall.entity.Collection;
import org.jeecg.modules.mall.entity.bo.CollectionProductBO;
import org.jeecg.modules.mall.mapper.CollectionMapper;
import org.jeecg.modules.mall.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 收藏信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements ICollectionService {
	
	@Autowired
	private CollectionMapper collectionMapper;
	
	@Override
	public List<Collection> selectByMainId(String mainId) {
		return collectionMapper.selectByMainId(mainId);
	}

	@Override
	public Collection queryByUserIdAndProductId(String userId, String productId) {
		return collectionMapper.queryByUserIdAndProductId(userId,productId);
	}

	@Override
	public List<CollectionProductBO> queryCollectionProductByUserId(String userId) {
		return collectionMapper.queryByUserIdAndProductId(userId);
	}
}
