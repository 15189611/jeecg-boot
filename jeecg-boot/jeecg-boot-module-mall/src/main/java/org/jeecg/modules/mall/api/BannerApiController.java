package org.jeecg.modules.mall.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.mall.config.MallConfig;
import org.jeecg.modules.mall.entity.Banner;
import org.jeecg.modules.mall.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
* @Description: Banner图
* @Author: jeecg-boot
* @Date:   2019-06-01
* @Version: V1.0
*/
@Slf4j
@Api(tags="Banner图")
@RestController
@RequestMapping("/api/mall/banner")
public class BannerApiController {
   @Autowired
   private IBannerService bannerService;

   @Autowired
   private MallConfig mallConfig;

   /**
     * 分页列表查询
    * @param banner
    * @param req
    * @return
    */
   @AutoLog(value = "Banner图-Api查询")
   @ApiOperation(value="Banner图-Api查询", notes="Banner图-Api查询Notes")
   @GetMapping(value = "/list")
   public Result<List<Banner>> queryPageList(Banner banner, HttpServletRequest req) {
       Result<List<Banner>> result = new Result<>();
       banner.setStatus(1);
       banner.setType(1);
       QueryWrapper<Banner> queryWrapper = new QueryWrapper<>(banner);
       queryWrapper.orderByAsc("sort");
       Page<Banner> page = new Page<>(1, 3);
       IPage<Banner> pageList = bannerService.page(page, queryWrapper);
       result.setSuccess(true);
       pageList.getRecords().forEach(e->e.setPicUrl(mallConfig.getPicPrefix()+e.getPicUrl()));
       result.setResult(pageList.getRecords());

       return result;
   }




}
