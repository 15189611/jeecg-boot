package org.jeecg.modules.support.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.support.entity.Image;

import java.util.List;

/**
 * @Description: 图片管理
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
public interface IImageService extends IService<Image> {

    List<Image> selectByBusinessId(String businessId);

    List<Image> selectByBusinessIdAndType(String businessId,Integer type);

}
