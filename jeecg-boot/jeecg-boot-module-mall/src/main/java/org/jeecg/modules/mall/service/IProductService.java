package org.jeecg.modules.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mall.entity.Collection;

import org.jeecg.modules.mall.entity.Product;
import org.jeecg.modules.support.entity.Image;

import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
public interface IProductService extends IService<Product> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Product product, List<Image> imageList, List<Collection> collectionList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(Product product, List<Image> imageList, List<Collection> collectionList);

	/**
	 * 删除一对多
	 */
	public void delMain(String id);

	/**
	 * 批量删除一对多
	 */
//	public void delBatchMain(Collection<? extends Serializable> idList);

	String getProductPic(String id);
	
}
