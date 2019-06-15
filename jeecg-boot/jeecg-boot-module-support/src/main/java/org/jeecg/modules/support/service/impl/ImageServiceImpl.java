package org.jeecg.modules.support.service.impl;

import org.jeecg.modules.support.entity.Image;
import org.jeecg.modules.support.mapper.ImageMapper;
import org.jeecg.modules.support.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 图片管理
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public List<Image> selectByBusinessId(String businessId) {
        return imageMapper.selectByBusinessId(businessId);
    }

    @Override
    public List<Image> selectByBusinessIdAndType(String businessId, Integer type) {
        return imageMapper.selectByBusinessIdAndType(businessId,type);
    }
}
