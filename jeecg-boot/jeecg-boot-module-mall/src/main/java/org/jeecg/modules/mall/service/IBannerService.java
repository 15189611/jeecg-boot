package org.jeecg.modules.mall.service;

import org.jeecg.modules.mall.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: Banner图
 * @Author: jeecg-boot
 * @Date:   2019-06-01
 * @Version: V1.0
 */
public interface IBannerService extends IService<Banner> {

    List<Banner> queryBanner();

}
