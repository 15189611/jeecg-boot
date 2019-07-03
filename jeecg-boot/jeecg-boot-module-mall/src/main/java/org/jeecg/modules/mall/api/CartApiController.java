package org.jeecg.modules.mall.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.mall.api.vo.ReqAddCart;
import org.jeecg.modules.mall.api.vo.ReqChangeNumCart;
import org.jeecg.modules.mall.api.vo.ReqRemoveCart;
import org.jeecg.modules.mall.entity.Cart;
import org.jeecg.modules.mall.entity.bo.CartProductBO;
import org.jeecg.modules.mall.service.ICartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 购物车
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品信息")
@RestController
@RequestMapping("/api/mall/cart")
public class CartApiController {
    @Autowired
    private ICartService cartService;


    /**
     * 分页列表查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/list")
    public Result<List<CartProductBO>> queryPageList(Cart cart,
                                            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Result<List<CartProductBO>> result = new Result<>();
        Page<Cart> page = new Page<>(pageNo, pageSize);
        IPage<CartProductBO> pageList = cartService.queryPageForUser(page, cart.getUserId());
        result.setSuccess(true);
        result.setResult(pageList.getRecords());
        return result;
    }


    @PostMapping(value = "/add")
    public Result add(ReqAddCart addCart) {
        Result<String> result = new Result<>();
        try {
            List<CartProductBO> list= cartService.queryListByUserIdAndProductIds(addCart.getUserId(), Arrays.asList(addCart.getProductId()));

            if(list.size()>0){
                result.setCode(1);
                result.setSuccess(true);
                result.setResult("已存在购物车中！");
                return result;
            }
            Cart cart =new Cart();
            BeanUtils.copyProperties(addCart, cart);
            cartService.save(cart);
            result.setCode(0);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }


    /**
     * 移出购物车
     * @param req
     * @return
     */

    @PostMapping(value = "/remove")
    public Result<Cart> remove(ReqRemoveCart req) {
        Result<Cart> result = new Result<Cart>();
        Cart cart = cartService.getById(req.getCartId());
        if(cart==null) {
            result.error500("未找到对应实体");
        }else {
            boolean ok = cartService.removeById(req.getCartId());
            if(ok) {
                result.setCode(0);
                result.setMessage("删除成功!");
            }
        }
        return result;
    }


    /**
     *  编辑
     * @param req
     * @return
     */
    @PostMapping(value = "/changeNum")
    public Result changeNum(ReqChangeNumCart req) {
        Result result = new Result();
        
        Cart cartEntity = cartService.getById(req.getCartId());
        if(cartEntity==null) {
            result.error500("未找到对应实体");
            return result;
        }

        if(cartEntity.getUserId().equals(req.getUserId())) {
            Cart cartDO = new Cart();
            cartDO.setId(req.getCartId());

            if("inc".equals(req.getAction())){
                cartDO.setNum(cartEntity.getNum()+1);
            }else{
                if(cartEntity.getNum()>1){
                    cartDO.setNum(cartEntity.getNum()-1);
                }
            }
            boolean ok = cartService.updateById(cartDO);
            if (ok) {
                result.setCode(0);
            }
        }

        return result;
    }



}
