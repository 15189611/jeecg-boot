package org.jeecg.modules.mall.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.entity.UserDetail;
import org.jeecg.modules.mall.entity.UserAccount;
import org.jeecg.modules.mall.vo.UserAccountPage;
import org.jeecg.modules.mall.service.IUserAccountService;
import org.jeecg.modules.mall.service.IUserAuthService;
import org.jeecg.modules.mall.service.IUserDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 用户信息
 * @Author: jeecg-boot
 * @Date:   2019-06-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/mall/userAccount")
@Slf4j
public class UserAccountController {
	@Autowired
	private IUserAccountService userAccountService;
	@Autowired
	private IUserAuthService userAuthService;
	@Autowired
	private IUserDetailService userDetailService;
	
	/**
	  * 分页列表查询
	 * @param userAccount
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<UserAccount>> queryPageList(UserAccount userAccount,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<UserAccount>> result = new Result<IPage<UserAccount>>();
		QueryWrapper<UserAccount> queryWrapper = QueryGenerator.initQueryWrapper(userAccount, req.getParameterMap());
		Page<UserAccount> page = new Page<UserAccount>(pageNo, pageSize);
		IPage<UserAccount> pageList = userAccountService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param userAccountPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<UserAccount> add(@RequestBody UserAccountPage userAccountPage) {
		Result<UserAccount> result = new Result<UserAccount>();
		try {
			UserAccount userAccount = new UserAccount();
			BeanUtils.copyProperties(userAccountPage, userAccount);
			
			userAccountService.save(userAccount);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param userAccountPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<UserAccount> edit(@RequestBody UserAccountPage userAccountPage) {
		Result<UserAccount> result = new Result<UserAccount>();
		UserAccount userAccount = new UserAccount();
		BeanUtils.copyProperties(userAccountPage, userAccount);
		UserAccount userAccountEntity = userAccountService.getById(userAccount.getId());
		if(userAccountEntity==null) {
			result.error500("未找到对应实体");
		}else {
			userAccountService.updateById(userAccount);
			result.success("修改成功!");
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<UserAccount> delete(@RequestParam(name="id",required=true) String id) {
		Result<UserAccount> result = new Result<UserAccount>();
		UserAccount userAccount = userAccountService.getById(id);
		if(userAccount==null) {
			result.error500("未找到对应实体");
		}else {
			userAccountService.delMain(id);
			result.success("删除成功!");
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<UserAccount> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<UserAccount> result = new Result<UserAccount>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.userAccountService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<UserAccount> queryById(@RequestParam(name="id",required=true) String id) {
		Result<UserAccount> result = new Result<UserAccount>();
		UserAccount userAccount = userAccountService.getById(id);
		if(userAccount==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(userAccount);
			result.setSuccess(true);
		}
		return result;
	}
	
	//===========================以下是子表信息操作相关API====================================
	
	/**
	  * 通过主表id查询授权信息
	 * @param mainId
	 * @return
	 */
	@GetMapping(value = "/listUserAuthByMainId")
	public Result<List<UserAuth>> queryUserAuthListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
		Result<List<UserAuth>> result = new Result<List<UserAuth>>();
		List<UserAuth> userAuthList = null;
		if (mainId != null) {
			userAuthList = userAuthService.selectByMainId(mainId);
            result.setResult(userAuthList);
            result.setSuccess(true);
            return result;
		}else return null;	
	}
	
	/**
     * 添加授权信息
     *
     * @param userAuth
     * @return
     */
    @PostMapping(value = "/addUserAuth")
    public Result<UserAuth> addUserAuth(@RequestBody UserAuth userAuth) {
        Result<UserAuth> result = new Result<>();
        try {
            boolean ok = userAuthService.save(userAuth);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("添加授权信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("添加授权信息失败!");
            }
            return result;
        } catch (Exception e) {
            e.fillInStackTrace();
            result.setSuccess(false);
            result.setMessage("添加授权信息过程中出现了异常: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 编辑授权信息
     *
     * @param userAuth
     * @return
     */
    @PutMapping("/editUserAuth")
    public Result<UserAuth> editUserAuth(@RequestBody UserAuth userAuth) {
        Result<UserAuth> result = new Result<>();
        try {
            boolean ok = userAuthService.updateById(userAuth);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("更新授权信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("更新授权信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 通过id删除授权信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteUserAuth")
    public Result<UserAuth> deleteUserAuth(@RequestParam(name = "id", required = true) String id) {
        Result<UserAuth> result = new Result<>();
        try {
            boolean ok = userAuthService.removeById(id);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("删除授权信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("删除授权信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除授权信息过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
	
	/**
     * 批量删除授权信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchUserAuth")
    public Result<UserAuth> deleteBatchUserAuth(@RequestParam(name = "ids", required = true) String ids) {
        Result<UserAuth> result = new Result<UserAuth>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.userAuthService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }
    
	/**
	  * 通过主表id查询消息信息
	 * @param mainId
	 * @return
	 */
	@GetMapping(value = "/listUserDetailByMainId")
	public Result<List<UserDetail>> queryUserDetailListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
		Result<List<UserDetail>> result = new Result<List<UserDetail>>();
		List<UserDetail> userDetailList = null;
		if (mainId != null) {
			userDetailList = userDetailService.selectByMainId(mainId);
            result.setResult(userDetailList);
            result.setSuccess(true);
            return result;
		}else return null;	
	}
	
	/**
     * 添加消息信息
     *
     * @param userDetail
     * @return
     */
    @PostMapping(value = "/addUserDetail")
    public Result<UserDetail> addUserDetail(@RequestBody UserDetail userDetail) {
        Result<UserDetail> result = new Result<>();
        try {
            boolean ok = userDetailService.save(userDetail);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("添加消息信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("添加消息信息失败!");
            }
            return result;
        } catch (Exception e) {
            e.fillInStackTrace();
            result.setSuccess(false);
            result.setMessage("添加消息信息过程中出现了异常: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 编辑消息信息
     *
     * @param userDetail
     * @return
     */
    @PutMapping("/editUserDetail")
    public Result<UserDetail> editUserDetail(@RequestBody UserDetail userDetail) {
        Result<UserDetail> result = new Result<>();
        try {
            boolean ok = userDetailService.updateById(userDetail);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("更新消息信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("更新消息信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 通过id删除消息信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteUserDetail")
    public Result<UserDetail> deleteUserDetail(@RequestParam(name = "id", required = true) String id) {
        Result<UserDetail> result = new Result<>();
        try {
            boolean ok = userDetailService.removeById(id);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("删除消息信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("删除消息信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除消息信息过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
	
	/**
     * 批量删除消息信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchUserDetail")
    public Result<UserDetail> deleteBatchUserDetail(@RequestParam(name = "ids", required = true) String ids) {
        Result<UserDetail> result = new Result<UserDetail>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.userDetailService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
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
        QueryWrapper<UserAccount> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                UserAccount userAccount = JSON.parseObject(deString, UserAccount.class);
                queryWrapper = QueryGenerator.initQueryWrapper(userAccount, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<UserAccountPage> pageList = new ArrayList<UserAccountPage>();
        List<UserAccount> userAccountList = userAccountService.list(queryWrapper);
        for (UserAccount userAccount : userAccountList) {
            UserAccountPage vo = new UserAccountPage();
            BeanUtils.copyProperties(userAccount, vo);
            List<UserAuth> userAuthList = userAuthService.selectByMainId(userAccount.getId());
            vo.setUserAuthList(userAuthList);
            List<UserDetail> userDetailList = userDetailService.selectByMainId(userAccount.getId());
            vo.setUserDetailList(userDetailList);
            pageList.add(vo);
        }
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "用户信息列表");
        mv.addObject(NormalExcelConstants.CLASS, UserAccountPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户信息列表数据", "导出人:Jeecg", "导出信息"));
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
                List<UserAccountPage> list = ExcelImportUtil.importExcel(file.getInputStream(), UserAccountPage.class, params);
                for (UserAccountPage page : list) {
                    UserAccount po = new UserAccount();
                    BeanUtils.copyProperties(page, po);
                    userAccountService.saveMain(po, page.getUserAuthList(),page.getUserDetailList());
                }
                return Result.ok("文件导入成功！数据行数:" + list.size());
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
