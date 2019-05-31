package org.jeecg.modules.crm.controller;

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
import org.jeecg.modules.crm.entity.Contacts;
import org.jeecg.modules.crm.entity.Trace;
import org.jeecg.modules.crm.entity.Customer;
import org.jeecg.modules.crm.vo.CustomerPage;
import org.jeecg.modules.crm.service.ICustomerService;
import org.jeecg.modules.crm.service.IContactsService;
import org.jeecg.modules.crm.service.ITraceService;
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
 * @Title: Controller
 * @Description: 客户信息
 * @author： jeecg-boot
 * @date：   2019-05-14
 * @version： V1.0
 */
@RestController
@RequestMapping("/crm/customer")
@Slf4j
public class CustomerController {
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IContactsService contactsService;
	@Autowired
	private ITraceService traceService;
	
	/**
	  * 分页列表查询
	 * @param customer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<Customer>> queryPageList(Customer customer,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Customer>> result = new Result<IPage<Customer>>();
		QueryWrapper<Customer> queryWrapper = QueryGenerator.initQueryWrapper(customer, req.getParameterMap());
		Page<Customer> page = new Page<Customer>(pageNo, pageSize);
		IPage<Customer> pageList = customerService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param customerPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Customer> add(@RequestBody CustomerPage customerPage) {
		Result<Customer> result = new Result<Customer>();
		try {
			Customer customer = new Customer();
			BeanUtils.copyProperties(customerPage, customer);
			
			customerService.save(customer);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param customerPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Customer> edit(@RequestBody CustomerPage customerPage) {
		Result<Customer> result = new Result<Customer>();
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerPage, customer);
		Customer customerEntity = customerService.getById(customer.getId());
		if(customerEntity==null) {
			result.error500("未找到对应实体");
		}else {
			customerService.updateById(customer);
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
	public Result<Customer> delete(@RequestParam(name="id",required=true) String id) {
		Result<Customer> result = new Result<Customer>();
		Customer customer = customerService.getById(id);
		if(customer==null) {
			result.error500("未找到对应实体");
		}else {
			customerService.delMain(id);
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
	public Result<Customer> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Customer> result = new Result<Customer>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.customerService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<Customer> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Customer> result = new Result<Customer>();
		Customer customer = customerService.getById(id);
		if(customer==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(customer);
			result.setSuccess(true);
		}
		return result;
	}
	
	//===========================以下是子表信息操作相关API====================================
	
	/**
	  * 通过主表id查询联系人
	 * @param mainId
	 * @return
	 */
	@GetMapping(value = "/listContactsByMainId")
	public Result<List<Contacts>> queryContactsListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
		Result<List<Contacts>> result = new Result<List<Contacts>>();
		List<Contacts> contactsList = null;
		if (mainId != null) {
			contactsList = contactsService.selectByMainId(mainId);
            result.setResult(contactsList);
            result.setSuccess(true);
            return result;
		}else return null;	
	}
	
	/**
     * 添加联系人
     *
     * @param contacts
     * @return
     */
    @PostMapping(value = "/addContacts")
    public Result<Contacts> addContacts(@RequestBody Contacts contacts) {
        Result<Contacts> result = new Result<>();
        try {
            boolean ok = contactsService.save(contacts);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("添加联系人成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("添加联系人失败!");
            }
            return result;
        } catch (Exception e) {
            e.fillInStackTrace();
            result.setSuccess(false);
            result.setMessage("添加联系人过程中出现了异常: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 编辑联系人
     *
     * @param contacts
     * @return
     */
    @PutMapping("/editContacts")
    public Result<Contacts> editContacts(@RequestBody Contacts contacts) {
        Result<Contacts> result = new Result<>();
        try {
            boolean ok = contactsService.updateById(contacts);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("更新联系人成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("更新联系人失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 通过id删除联系人
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteContacts")
    public Result<Contacts> deleteContacts(@RequestParam(name = "id", required = true) String id) {
        Result<Contacts> result = new Result<>();
        try {
            boolean ok = contactsService.removeById(id);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("删除联系人成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("删除联系人失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除联系人过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
	
	/**
     * 批量删除联系人
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchContacts")
    public Result<Contacts> deleteBatchContacts(@RequestParam(name = "ids", required = true) String ids) {
        Result<Contacts> result = new Result<Contacts>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.contactsService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }
    
	/**
	  * 通过主表id查询跟进信息
	 * @param mainId
	 * @return
	 */
	@GetMapping(value = "/listTraceByMainId")
	public Result<List<Trace>> queryTraceListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
		Result<List<Trace>> result = new Result<List<Trace>>();
		List<Trace> traceList = null;
		if (mainId != null) {
			traceList = traceService.selectByMainId(mainId);
            result.setResult(traceList);
            result.setSuccess(true);
            return result;
		}else return null;	
	}
	
	/**
     * 添加跟进信息
     *
     * @param trace
     * @return
     */
    @PostMapping(value = "/addTrace")
    public Result<Trace> addTrace(@RequestBody Trace trace) {
        Result<Trace> result = new Result<>();
        try {
            boolean ok = traceService.save(trace);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("添加跟进信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("添加跟进信息失败!");
            }
            return result;
        } catch (Exception e) {
            e.fillInStackTrace();
            result.setSuccess(false);
            result.setMessage("添加跟进信息过程中出现了异常: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 编辑跟进信息
     *
     * @param trace
     * @return
     */
    @PutMapping("/editTrace")
    public Result<Trace> editTrace(@RequestBody Trace trace) {
        Result<Trace> result = new Result<>();
        try {
            boolean ok = traceService.updateById(trace);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("更新跟进信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("更新跟进信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 通过id删除跟进信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteTrace")
    public Result<Trace> deleteTrace(@RequestParam(name = "id", required = true) String id) {
        Result<Trace> result = new Result<>();
        try {
            boolean ok = traceService.removeById(id);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("删除跟进信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("删除跟进信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除跟进信息过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }
	
	/**
     * 批量删除跟进信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchTrace")
    public Result<Trace> deleteBatchTrace(@RequestParam(name = "ids", required = true) String ids) {
        Result<Trace> result = new Result<Trace>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.traceService.removeByIds(Arrays.asList(ids.split(",")));
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
        QueryWrapper<Customer> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                Customer customer = JSON.parseObject(deString, Customer.class);
                queryWrapper = QueryGenerator.initQueryWrapper(customer, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<CustomerPage> pageList = new ArrayList<CustomerPage>();
        List<Customer> customerList = customerService.list(queryWrapper);
        for (Customer customer : customerList) {
            CustomerPage vo = new CustomerPage();
            BeanUtils.copyProperties(customer, vo);
            List<Contacts> contactsList = contactsService.selectByMainId(customer.getId());
            vo.setContactsList(contactsList);
            List<Trace> traceList = traceService.selectByMainId(customer.getId());
            vo.setTraceList(traceList);
            pageList.add(vo);
        }
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "客户信息列表");
        mv.addObject(NormalExcelConstants.CLASS, CustomerPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("客户信息列表数据", "导出人:Jeecg", "导出信息"));
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
                List<CustomerPage> list = ExcelImportUtil.importExcel(file.getInputStream(), CustomerPage.class, params);
                for (CustomerPage page : list) {
                    Customer po = new Customer();
                    BeanUtils.copyProperties(page, po);
                    customerService.saveMain(po, page.getContactsList(),page.getTraceList());
                }
                return Result.ok("文件导入成功！数据行数：" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage());
                return Result.error("文件导入失败！");
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
