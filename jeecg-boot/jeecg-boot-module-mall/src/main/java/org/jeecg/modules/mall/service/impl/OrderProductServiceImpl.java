package org.jeecg.modules.mall.service.impl;

import org.jeecg.modules.mall.entity.OrderProduct;
import org.jeecg.modules.mall.entity.bo.OrderProductBO;
import org.jeecg.modules.mall.mapper.OrderProductMapper;
import org.jeecg.modules.mall.service.IOrderProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 订单商品
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Service
public class OrderProductServiceImpl extends ServiceImpl<OrderProductMapper, OrderProduct> implements IOrderProductService {
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	
	@Override
	public List<OrderProductBO> selectByMainId(String mainId) {
		return orderProductMapper.selectByMainId(mainId);
	}
}
