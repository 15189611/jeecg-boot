package org.jeecg.modules.crm.service;

import org.jeecg.modules.crm.entity.Trace;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 跟进信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
public interface ITraceService extends IService<Trace> {

	public List<Trace> selectByMainId(String mainId);
}
