package org.jeecg.modules.mall.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.mall.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * @Description: 订单信息
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
public interface OrderMapper extends BaseMapper<Order> {


    IPage<Order> queryOrderByUserIdAndStatus(Page page, @Param("userId") String userId,Integer status,List<Integer> statusList );

    @Select("select * from t_shop_order where order_no  = #{orderNo}")
    Order queryByOrderNo(String orderNo);
}
