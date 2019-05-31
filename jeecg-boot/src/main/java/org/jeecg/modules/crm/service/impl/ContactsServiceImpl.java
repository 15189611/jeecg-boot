package org.jeecg.modules.crm.service.impl;

import org.jeecg.modules.crm.entity.Contacts;
import org.jeecg.modules.crm.mapper.ContactsMapper;
import org.jeecg.modules.crm.service.IContactsService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 联系人
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
@Service
public class ContactsServiceImpl extends ServiceImpl<ContactsMapper, Contacts> implements IContactsService {
	
	@Autowired
	private ContactsMapper contactsMapper;
	
	@Override
	public List<Contacts> selectByMainId(String mainId) {
		return contactsMapper.selectByMainId(mainId);
	}
}
