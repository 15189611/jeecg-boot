package org.jeecg.modules.mall.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mall.entity.Cart;
import org.jeecg.modules.mall.service.ICartService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 购物车
 * @Author: jeecg-boot
 * @Date:   2019-06-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="购物车")
@RestController
@RequestMapping("/mall/cart")
public class CartController {
	@Autowired
	private ICartService cartService;
	
	/**
	  * 分页列表查询
	 * @param cart
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "购物车-分页列表查询")
	@ApiOperation(value="购物车-分页列表查询", notes="购物车-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Cart>> queryPageList(Cart cart,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Cart>> result = new Result<IPage<Cart>>();
		QueryWrapper<Cart> queryWrapper = QueryGenerator.initQueryWrapper(cart, req.getParameterMap());
		Page<Cart> page = new Page<Cart>(pageNo, pageSize);
		IPage<Cart> pageList = cartService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param cart
	 * @return
	 */
	@AutoLog(value = "购物车-添加")
	@ApiOperation(value="购物车-添加", notes="购物车-添加")
	@PostMapping(value = "/add")
	public Result<Cart> add(@RequestBody Cart cart) {
		Result<Cart> result = new Result<Cart>();
		try {
			cartService.save(cart);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param cart
	 * @return
	 */
	@AutoLog(value = "购物车-编辑")
	@ApiOperation(value="购物车-编辑", notes="购物车-编辑")
	@PutMapping(value = "/edit")
	public Result<Cart> edit(@RequestBody Cart cart) {
		Result<Cart> result = new Result<Cart>();
		Cart cartEntity = cartService.getById(cart.getId());
		if(cartEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = cartService.updateById(cart);
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
	@AutoLog(value = "购物车-通过id删除")
	@ApiOperation(value="购物车-通过id删除", notes="购物车-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<Cart> delete(@RequestParam(name="id",required=true) String id) {
		Result<Cart> result = new Result<Cart>();
		Cart cart = cartService.getById(id);
		if(cart==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = cartService.removeById(id);
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
	@AutoLog(value = "购物车-批量删除")
	@ApiOperation(value="购物车-批量删除", notes="购物车-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Cart> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Cart> result = new Result<Cart>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.cartService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "购物车-通过id查询")
	@ApiOperation(value="购物车-通过id查询", notes="购物车-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Cart> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Cart> result = new Result<Cart>();
		Cart cart = cartService.getById(id);
		if(cart==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(cart);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<Cart> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Cart cart = JSON.parseObject(deString, Cart.class);
              queryWrapper = QueryGenerator.initQueryWrapper(cart, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Cart> pageList = cartService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "购物车列表");
      mv.addObject(NormalExcelConstants.CLASS, Cart.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("购物车列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<Cart> listCarts = ExcelImportUtil.importExcel(file.getInputStream(), Cart.class, params);
              for (Cart cartExcel : listCarts) {
                  cartService.save(cartExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listCarts.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
