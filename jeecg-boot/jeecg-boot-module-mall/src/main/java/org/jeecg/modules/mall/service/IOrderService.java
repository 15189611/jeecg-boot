package org.jeecg.modules.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mall.entity.OrderProduct;
import org.jeecg.modules.mall.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 订单信息
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
public interface IOrderService extends IService<Order> {

	/**
	 * 添加一对多
	 * 
	 */
	String saveMain(Order order,List<OrderProduct> orderProductList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	void updateMain(Order order,List<OrderProduct> orderProductList);
	
	/**
	 * 删除一对多
	 */
	void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	void delBatchMain (Collection<? extends Serializable> idList);

	/**
	 * 按用户ID查询指定status的订单
	 * @return
	 */
	IPage queryOrderByUserIdAndStatus(Page page, String userId, Integer status);

	/**
	 * 按用户ID查询所有订单 不包括 status=0的
	 */
	IPage queryAllOrderByUserId(Page page, String userId);

	Order queryByOrderNo(String orderNo);
	
}
