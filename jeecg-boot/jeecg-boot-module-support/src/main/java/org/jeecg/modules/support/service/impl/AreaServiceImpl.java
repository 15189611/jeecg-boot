package org.jeecg.modules.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.support.entity.Area;
import org.jeecg.modules.support.mapper.AreaMapper;
import org.jeecg.modules.support.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 区域信息
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<Area> queryAreaByParent(String parentCode) {
        Area area = new Area();
        area.setParentCode(parentCode);
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>(area);
        return areaMapper.selectList(queryWrapper);
    }
}
