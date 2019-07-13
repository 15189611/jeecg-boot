package org.jeecg.modules.mall.service.impl;

import org.jeecg.modules.mall.entity.ProductDetail;
import org.jeecg.modules.mall.mapper.ProductDetailMapper;
import org.jeecg.modules.mall.service.IProductDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 商品详细信息
 * @Author: jeecg-boot
 * @Date:   2019-07-13
 * @Version: V1.0
 */
@Service
public class ProductDetailServiceImpl extends ServiceImpl<ProductDetailMapper, ProductDetail> implements IProductDetailService {
	
	@Autowired
	private ProductDetailMapper productDetailMapper;
	
	@Override
	public ProductDetail selectByMainId(String mainId) {
		ProductDetail result=null;
		List<ProductDetail> list =  productDetailMapper.selectByMainId(mainId);
		if(list!=null && list.size()>0){
			result = list.get(0);
		}
		return result;
	}
}
