package org.jeecg.modules.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.mall.entity.Collection;
import org.jeecg.modules.mall.entity.bo.CollectionProductBO;

import java.util.List;

/**
 * @Description: 收藏信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
public interface CollectionMapper extends BaseMapper<Collection> {


	boolean deleteByMainId(String mainId);
    
	List<Collection> selectByMainId(String mainId);

	@Select("select * from t_shop_collection where user_id=#{userId} and product_id=#{productId}")
	Collection queryByUserIdAndProductId(@Param("userId") String userId,@Param("productId")  String productId);

	List<CollectionProductBO> queryByUserIdAndProductId(String userId);
}
