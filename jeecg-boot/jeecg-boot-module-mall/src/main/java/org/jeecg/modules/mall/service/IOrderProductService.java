package org.jeecg.modules.mall.service;

import org.jeecg.modules.mall.entity.OrderProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mall.entity.bo.OrderProductBO;

import java.util.List;

/**
 * @Description: 订单商品
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
public interface IOrderProductService extends IService<OrderProduct> {

	List<OrderProductBO> selectByMainId(String mainId);
}
