package org.jeecg.modules.mall.mapper;

import java.util.List;
import org.jeecg.modules.mall.entity.ProductDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 商品详细信息
 * @Author: jeecg-boot
 * @Date:   2019-07-13
 * @Version: V1.0
 */
public interface ProductDetailMapper extends BaseMapper<ProductDetail> {

	public boolean deleteByMainId(String mainId);
    
	public List<ProductDetail> selectByMainId(String mainId);
}
