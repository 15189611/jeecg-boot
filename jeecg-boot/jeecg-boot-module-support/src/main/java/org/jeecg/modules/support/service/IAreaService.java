package org.jeecg.modules.support.service;

import org.jeecg.modules.support.entity.Area;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 区域信息
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
public interface IAreaService extends IService<Area> {

    List<Area> queryAreaByParent(String parentCode);

}
