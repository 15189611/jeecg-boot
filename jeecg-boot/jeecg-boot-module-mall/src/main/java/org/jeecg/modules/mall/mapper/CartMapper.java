package org.jeecg.modules.mall.mapper;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mall.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.mall.entity.bo.CartProductBO;

import java.util.List;

/**
 * @Description: 购物车
 * @Author: jeecg-boot
 * @Date:   2019-06-20
 * @Version: V1.0
 */
public interface CartMapper extends BaseMapper<Cart> {

    IPage<CartProductBO> queryPageForUser(Page page, @Param("userId") String userId);

    List<CartProductBO> queryListByUserIdAndProductIds(String userId, @Param("produceIdList") List<String> productIdList);

    List<CartProductBO> queryListByUserIdAndIds(String userId, @Param("idList") List<String> ids);



}
