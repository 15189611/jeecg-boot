package org.jeecg.modules.crm.service.impl;

import org.jeecg.modules.crm.entity.Trace;
import org.jeecg.modules.crm.mapper.TraceMapper;
import org.jeecg.modules.crm.service.ITraceService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 跟进信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
@Service
public class TraceServiceImpl extends ServiceImpl<TraceMapper, Trace> implements ITraceService {
	
	@Autowired
	private TraceMapper traceMapper;
	
	@Override
	public List<Trace> selectByMainId(String mainId) {
		return traceMapper.selectByMainId(mainId);
	}
}
