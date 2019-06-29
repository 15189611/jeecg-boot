package org.jeecg.modules.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mall.entity.Collection;
import org.jeecg.modules.mall.entity.bo.CollectionProductBO;

import java.util.List;

/**
 * @Description: 收藏信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
public interface ICollectionService extends IService<Collection> {


	List<Collection> selectByMainId(String mainId);

	Collection queryByUserIdAndProductId(String userId,String productId);

	List<CollectionProductBO> queryCollectionProductByUserId(String userId);


}
