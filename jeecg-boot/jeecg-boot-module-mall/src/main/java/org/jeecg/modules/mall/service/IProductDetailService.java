package org.jeecg.modules.mall.service;

import org.jeecg.modules.mall.entity.ProductDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 商品详细信息
 * @Author: jeecg-boot
 * @Date:   2019-07-13
 * @Version: V1.0
 */
public interface IProductDetailService extends IService<ProductDetail> {

	ProductDetail selectByMainId(String mainId);
}
