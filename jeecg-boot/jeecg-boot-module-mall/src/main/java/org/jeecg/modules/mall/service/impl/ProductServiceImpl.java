package org.jeecg.modules.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.mall.entity.Collection;
import org.jeecg.modules.mall.entity.Product;
import org.jeecg.modules.mall.mapper.CollectionMapper;
import org.jeecg.modules.mall.mapper.ProductMapper;
import org.jeecg.modules.mall.service.IProductService;
import org.jeecg.modules.support.entity.Image;
import org.jeecg.modules.support.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ImageMapper imageMapper;
	@Autowired
	private CollectionMapper collectionMapper;
	
	@Override
	@Transactional
	public void saveMain(Product product, List<Image> imageList, List<Collection> collectionList) {
		productMapper.insert(product);
		for(Image entity:imageList) {
			//外键设置
			entity.setBusinessId(product.getId());
			imageMapper.insert(entity);
		}
		for(Collection entity:collectionList) {
			//外键设置
			entity.setProductId(product.getId());
			collectionMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void updateMain(Product product,List<Image> imageList,List<Collection> collectionList) {
		productMapper.updateById(product);
		
		//1.先删除子表数据
		imageMapper.deleteByBusinessId(product.getId());
		collectionMapper.deleteByMainId(product.getId());
		
		//2.子表数据重新插入
		for(Image entity:imageList) {
			//外键设置
			entity.setBusinessId(product.getId());
			imageMapper.insert(entity);
		}
		for(Collection entity:collectionList) {
			//外键设置
			entity.setProductId(product.getId());
			collectionMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		imageMapper.deleteByBusinessId(id);
		collectionMapper.deleteByMainId(id);
		productMapper.deleteById(id);
	}

//	@Override
//	@Transactional
//	public void delBatchMain(Collection<? extends Serializable> idList) {
//		for(Serializable id: idList) {
//			imageMapper.deleteByMainId(id.toString());
//			collectionMapper.deleteByMainId(id.toString());
//			productMapper.deleteById(id);
//		}
//	}


	@Override
	public String getProductPic(String id) {
		List<Image> list = imageMapper.selectByBusinessIdAndType(id,1);
		if(list!=null && list.size()>0){
			Image image = list.get(0);
			return image.getUrl();
		}
		return "";
	}
}
