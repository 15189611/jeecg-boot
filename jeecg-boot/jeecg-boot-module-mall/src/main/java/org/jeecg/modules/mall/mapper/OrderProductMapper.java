package org.jeecg.modules.mall.mapper;

import java.util.List;
import org.jeecg.modules.mall.entity.OrderProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.mall.entity.bo.OrderProductBO;

/**
 * @Description: 订单商品
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
public interface OrderProductMapper extends BaseMapper<OrderProduct> {

	boolean deleteByMainId(String mainId);
    
	List<OrderProductBO> selectByMainId(String mainId);
}
