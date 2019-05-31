package org.jeecg.modules.crm.service;

import org.jeecg.modules.crm.entity.Contacts;
import org.jeecg.modules.crm.entity.Trace;
import org.jeecg.modules.crm.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 客户信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
public interface ICustomerService extends IService<Customer> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Customer customer,List<Contacts> contactsList,List<Trace> traceList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Customer customer,List<Contacts> contactsList,List<Trace> traceList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
