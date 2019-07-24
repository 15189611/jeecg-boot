package org.jeecg.modules.crm.mapper;

import java.util.List;
import org.jeecg.modules.crm.entity.Contacts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 联系人
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
public interface ContactsMapper extends BaseMapper<Contacts> {

	public boolean deleteByMainId(String mainId);
    
	public List<Contacts> selectByMainId(String mainId);
}
