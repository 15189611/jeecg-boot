package org.jeecg.modules.mall.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.mall.entity.Banner;
import org.jeecg.modules.mall.service.IBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: Banner图
 * @Author: jeecg-boot
 * @Date:   2019-06-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="Banner图")
@RestController
@RequestMapping("/mall/banner")
public class BannerController {
	@Autowired
	private IBannerService bannerService;
	
	/**
	  * 分页列表查询
	 * @param banner
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "Banner图-分页列表查询")
	@ApiOperation(value="Banner图-分页列表查询", notes="Banner图-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Banner>> queryPageList(Banner banner,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Banner>> result = new Result<IPage<Banner>>();
		QueryWrapper<Banner> queryWrapper = QueryGenerator.initQueryWrapper(banner, req.getParameterMap());
		Page<Banner> page = new Page<Banner>(pageNo, pageSize);
		IPage<Banner> pageList = bannerService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param banner
	 * @return
	 */
	@AutoLog(value = "Banner图-添加")
	@ApiOperation(value="Banner图-添加", notes="Banner图-添加")
	@PostMapping(value = "/add")
	public Result<Banner> add(@RequestBody Banner banner) {
		Result<Banner> result = new Result<Banner>();
		try {
			bannerService.save(banner);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param banner
	 * @return
	 */
	@AutoLog(value = "Banner图-编辑")
	@ApiOperation(value="Banner图-编辑", notes="Banner图-编辑")
	@PutMapping(value = "/edit")
	public Result<Banner> edit(@RequestBody Banner banner) {
		Result<Banner> result = new Result<Banner>();
		Banner bannerEntity = bannerService.getById(banner.getId());
		if(bannerEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = bannerService.updateById(banner);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "Banner图-通过id删除")
	@ApiOperation(value="Banner图-通过id删除", notes="Banner图-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<Banner> delete(@RequestParam(name="id",required=true) String id) {
		Result<Banner> result = new Result<Banner>();
		Banner banner = bannerService.getById(id);
		if(banner==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = bannerService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "Banner图-批量删除")
	@ApiOperation(value="Banner图-批量删除", notes="Banner图-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Banner> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Banner> result = new Result<Banner>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.bannerService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "Banner图-通过id查询")
	@ApiOperation(value="Banner图-通过id查询", notes="Banner图-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Banner> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Banner> result = new Result<Banner>();
		Banner banner = bannerService.getById(id);
		if(banner==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(banner);
			result.setSuccess(true);
		}
		return result;
	}

}
