package org.jeecg.modules.crm.service.impl;

import org.jeecg.modules.crm.entity.Customer;
import org.jeecg.modules.crm.entity.Contacts;
import org.jeecg.modules.crm.entity.Trace;
import org.jeecg.modules.crm.mapper.ContactsMapper;
import org.jeecg.modules.crm.mapper.TraceMapper;
import org.jeecg.modules.crm.mapper.CustomerMapper;
import org.jeecg.modules.crm.service.ICustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 客户信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private ContactsMapper contactsMapper;
	@Autowired
	private TraceMapper traceMapper;
	
	@Override
	@Transactional
	public void saveMain(Customer customer, List<Contacts> contactsList,List<Trace> traceList) {
		customerMapper.insert(customer);
		for(Contacts entity:contactsList) {
			//外键设置
			entity.setCustomerId(customer.getId());
			contactsMapper.insert(entity);
		}
		for(Trace entity:traceList) {
			//外键设置
			entity.setCustomerId(customer.getId());
			traceMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void updateMain(Customer customer,List<Contacts> contactsList,List<Trace> traceList) {
		customerMapper.updateById(customer);
		
		//1.先删除子表数据
		contactsMapper.deleteByMainId(customer.getId());
		traceMapper.deleteByMainId(customer.getId());
		
		//2.子表数据重新插入
		for(Contacts entity:contactsList) {
			//外键设置
			entity.setCustomerId(customer.getId());
			contactsMapper.insert(entity);
		}
		for(Trace entity:traceList) {
			//外键设置
			entity.setCustomerId(customer.getId());
			traceMapper.insert(entity);
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		customerMapper.deleteById(id);
		contactsMapper.deleteByMainId(id);
		traceMapper.deleteByMainId(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			customerMapper.deleteById(id);
			contactsMapper.deleteByMainId(id.toString());
			traceMapper.deleteByMainId(id.toString());
		}
	}
	
}
