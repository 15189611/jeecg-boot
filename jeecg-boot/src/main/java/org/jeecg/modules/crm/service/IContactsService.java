package org.jeecg.modules.crm.service;

import org.jeecg.modules.crm.entity.Contacts;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 联系人
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
public interface IContactsService extends IService<Contacts> {

	public List<Contacts> selectByMainId(String mainId);
}
