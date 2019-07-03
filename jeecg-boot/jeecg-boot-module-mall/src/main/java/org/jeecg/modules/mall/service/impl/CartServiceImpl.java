package org.jeecg.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mall.entity.Cart;
import org.jeecg.modules.mall.entity.bo.CartProductBO;
import org.jeecg.modules.mall.mapper.CartMapper;
import org.jeecg.modules.mall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 购物车
 * @Author: jeecg-boot
 * @Date:   2019-06-20
 * @Version: V1.0
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Override
    public IPage<CartProductBO> queryPageForUser(Page page, String usrId) {
        return cartMapper.queryPageForUser(page,usrId);
    }

    @Override
    public List<CartProductBO> queryListByUserIdAndProductIds(String userId, List<String> ids) {
        return cartMapper.queryListByUserIdAndProductIds(userId,ids);
    }

    @Override
    public List<CartProductBO> queryListByUserIdAndIds(String userId, List<String> ids) {
        return cartMapper.queryListByUserIdAndIds(userId,ids);
    }
}
