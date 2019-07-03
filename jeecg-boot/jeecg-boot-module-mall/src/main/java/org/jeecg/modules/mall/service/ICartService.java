package org.jeecg.modules.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.mall.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mall.entity.bo.CartProductBO;

import java.util.List;

/**
 * @Description: 购物车
 * @Author: jeecg-boot
 * @Date:   2019-06-20
 * @Version: V1.0
 */
public interface ICartService extends IService<Cart> {

    IPage queryPageForUser(Page page, String userId);

    /**
     * 查看产品是否存在购物车
     * @param userId
     * @param ids
     * @return
     */
    List<CartProductBO> queryListByUserIdAndProductIds(String userId, List<String> ids);

    /**
     * 通过用户ID和购物车IDS获取特定购物车列表
     * @param userId
     * @param ids
     * @return
     */
    List<CartProductBO> queryListByUserIdAndIds(String userId, List<String> ids);

}
