package org.jeecg.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.mall.entity.Banner;
import org.jeecg.modules.mall.mapper.BannerMapper;
import org.jeecg.modules.mall.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: Banner图
 * @Author: jeecg-boot
 * @Date:   2019-06-01
 * @Version: V1.0
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> queryBanner() {
        Banner banner = new Banner();
        banner.setStatus(1);
        QueryWrapper query = new QueryWrapper<>(banner);
        Page<Banner> page = new Page<Banner>(1, 3);
        IPage<Banner> pageList = bannerMapper.selectPage(page,query);
        return pageList.getRecords();
    }
}
