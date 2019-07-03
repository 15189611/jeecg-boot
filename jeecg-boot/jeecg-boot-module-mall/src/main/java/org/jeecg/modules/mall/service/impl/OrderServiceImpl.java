package org.jeecg.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mall.entity.Order;
import org.jeecg.modules.mall.entity.OrderProduct;
import org.jeecg.modules.mall.mapper.OrderProductMapper;
import org.jeecg.modules.mall.mapper.OrderMapper;
import org.jeecg.modules.mall.service.IOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 订单信息
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderProductMapper orderProductMapper;
	
	@Override
	@Transactional
	public String saveMain(Order order, List<OrderProduct> orderProductList) {
		orderMapper.insert(order);
		for(OrderProduct entity:orderProductList) {
			//外键设置
			entity.setOrderId(order.getId());
			orderProductMapper.insert(entity);
		}
		return order.getId();
	}

	@Override
	@Transactional
	public void updateMain(Order order,List<OrderProduct> orderProductList) {
		orderMapper.updateById(order);
		
		//1.先删除子表数据
		orderProductMapper.deleteByMainId(order.getId());
		
		//2.子表数据重新插入
		for(OrderProduct entity:orderProductList) {
			//外键设置
			entity.setProductId(order.getId());
			orderProductMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		orderProductMapper.deleteByMainId(id);
		orderMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			orderProductMapper.deleteByMainId(id.toString());
			orderMapper.deleteById(id);
		}
	}

	@Override
	public IPage<Order> queryOrderByUserIdAndStatus(Page page, String userId, Integer status) {
		return orderMapper.queryOrderByUserIdAndStatus(page,userId,status,null);
	}

	@Override
	public IPage queryAllOrderByUserId(Page page, String userId) {
		List<Integer> statusList = new ArrayList<>();
		statusList.add(1);
		statusList.add(2);
		statusList.add(3);
		statusList.add(4);
		return orderMapper.queryOrderByUserIdAndStatus(page,userId,null,statusList);
	}

	@Override
	public Order queryByOrderNo(String orderNo) {
		return orderMapper.queryByOrderNo(orderNo);
	}
}
