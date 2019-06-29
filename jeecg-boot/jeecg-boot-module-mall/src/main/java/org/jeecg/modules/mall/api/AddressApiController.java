package org.jeecg.modules.mall.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.mall.entity.Address;
import org.jeecg.modules.mall.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
* @Description: 收获地址
* @Author: jeecg-boot
* @Date:   2019-06-22
* @Version: V1.0
*/
@Slf4j
@Api(tags="收获地址")
@RestController
@RequestMapping("/api/mall/address")
public class AddressApiController {
   @Autowired
   private IAddressService addressService;

   /**
     * 分页列表查询
    * @param address
    * @param pageNo
    * @param pageSize
    * @return
    */
   @AutoLog(value = "收获地址-分页列表查询")
   @ApiOperation(value="收获地址-分页列表查询", notes="收获地址-分页列表查询")
   @PostMapping(value = "/list")
   public Result<List<Address>> queryPageList(Address address,
                                              @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                              @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
       Result<List<Address>> result = new Result<>();
       QueryWrapper<Address> queryWrapper = new QueryWrapper<>(address);
       Page<Address> page = new Page<>(pageNo, pageSize);
       IPage<Address> pageList = addressService.page(page, queryWrapper);
       result.setSuccess(true);
       result.setResult(pageList.getRecords());
       return result;
   }

   /**
     *   添加
    * @param address
    * @return
    */
   @AutoLog(value = "收获地址-添加")
   @ApiOperation(value="收获地址-添加", notes="收获地址-添加")
   @PostMapping(value = "/add")
   public Result<Address> add(@RequestBody Address address) {
       Result<Address> result = new Result<Address>();
       try {
           addressService.save(address);
           result.success("添加成功！");
       } catch (Exception e) {
           log.error(e.getMessage(),e);
           result.error500("操作失败");
       }
       return result;
   }

   /**
     *  编辑
    * @param address
    * @return
    */
   @AutoLog(value = "收获地址-编辑")
   @ApiOperation(value="收获地址-编辑", notes="收获地址-编辑")
   @PutMapping(value = "/edit")
   public Result<Address> edit(@RequestBody Address address) {
       Result<Address> result = new Result<Address>();
       Address addressEntity = addressService.getById(address.getId());
       if(addressEntity==null) {
           result.error500("未找到对应实体");
       }else {
           boolean ok = addressService.updateById(address);
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
   @AutoLog(value = "收获地址-通过id删除")
   @ApiOperation(value="收获地址-通过id删除", notes="收获地址-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<Address> delete(@RequestParam(name="id",required=true) String id) {
       Result<Address> result = new Result<Address>();
       Address address = addressService.getById(id);
       if(address==null) {
           result.error500("未找到对应实体");
       }else {
           boolean ok = addressService.removeById(id);
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
   @AutoLog(value = "收获地址-批量删除")
   @ApiOperation(value="收获地址-批量删除", notes="收获地址-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<Address> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       Result<Address> result = new Result<Address>();
       if(ids==null || "".equals(ids.trim())) {
           result.error500("参数不识别！");
       }else {
           this.addressService.removeByIds(Arrays.asList(ids.split(",")));
           result.success("删除成功!");
       }
       return result;
   }

   /**
     * 通过id查询
    * @param id
    * @return
    */
   @AutoLog(value = "收获地址-通过id查询")
   @ApiOperation(value="收获地址-通过id查询", notes="收获地址-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<Address> queryById(@RequestParam(name="id",required=true) String id) {
       Result<Address> result = new Result<Address>();
       Address address = addressService.getById(id);
       if(address==null) {
           result.error500("未找到对应实体");
       }else {
           result.setResult(address);
           result.setSuccess(true);
       }
       return result;
   }

}
