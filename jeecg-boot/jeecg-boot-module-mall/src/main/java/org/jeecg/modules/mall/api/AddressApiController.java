package org.jeecg.modules.mall.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.mall.api.vo.DefaultAddress;
import org.jeecg.modules.mall.api.vo.QueryAddress;
import org.jeecg.modules.mall.api.vo.ReqAddAddress;
import org.jeecg.modules.mall.api.vo.ReqRemoveAddress;
import org.jeecg.modules.mall.entity.Address;
import org.jeecg.modules.mall.service.IAddressService;
import org.jeecg.modules.support.entity.Area;
import org.jeecg.modules.support.service.IAreaService;
import org.springframework.beans.BeanUtils;
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
   @Autowired
   private IAreaService areaService;

   /**
     * 分页列表查询
    * @param address
    * @param pageNo
    * @param pageSize
    * @return
    */
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
    * @param req
    * @return
    */
   @PostMapping(value = "/add")
   public Result<Address> add(ReqAddAddress req) {
       Result<Address> result = new Result();
       try {
           Address address = new Address();
           if("edit".equals(req.getAction())){
               address = addressService.getById(req.getAddressId());
           }
           BeanUtils.copyProperties(req, address);

           Address defaultAddress = addressService.queryUserDefaultAddress(req.getUserId());
           if(defaultAddress!=null){
               address.setIsDefault(0);
           }else{
               address.setIsDefault(1);
           }
           Area area = areaService.getById(req.getDistrictCode());
           address.setProvinceName(req.getProvinceCode());
           address.setCityName(req.getCityCode());
           address.setDistrictName(req.getDistrictCode());
           address.setArea(area.getProvinceName()+area.getCityName()+area.getAreaName());
           address.setAddress(req.getAddress());
           if("edit".equals(req.getAction())){
               addressService.updateById(address);
           }else{
               addressService.save(address);
           }
           result.setCode(0);
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
    * @param req
    * @return
    */
   @PostMapping(value = "/remove")
   public Result<Address> delete(ReqRemoveAddress req) {
       Result<Address> result = new Result<Address>();
       Address address = addressService.getById(req.getAddressId());
       if(address==null) {
           result.error500("未找到对应实体");
       }else {
           if(address.getUserId().equals(req.getUserId())){
               boolean ok = addressService.removeById(address.getId());
               if(ok) {
                   result.success("删除成功!");
               }
           }
       }
       return result;
   }


   /**
     * 通过id查询
    * @param
    * @return
    */
   @PostMapping(value = "/queryById")
   public Result queryById(QueryAddress query){
       Result result = new Result();
       Address address=null;
       if(StringUtils.isNotEmpty(query.getAddressId())&& !"undefined".equals(query.getAddressId())){
           //返回地址
           address = addressService.getById(query.getAddressId());
           if(! query.getUserId().equals(address.getUserId())){
               address=null;
           }
       }else{
           //获取用户默认地址信息
            address = addressService.queryUserDefaultAddress(query.getUserId());
       }

       if(address==null) {
           result.error500("未找到对应实体");
       }else {
           result.setResult(address);
           result.setSuccess(true);
       }
       return result;
   }


    /**
     * 设置默认地址
     * @param address
     * @return
     */
    @PostMapping(value = "/default")
    public Result setDefault(DefaultAddress address) {
        Result result = new Result();

        Address dto = addressService.getById(address.getAddressId());
        if(dto==null || !dto.getUserId().equals(address.getUserId())){
            result.setCode(1);
            result.setMessage("设置失败！");
            return result;
        }
        addressService.setDefaultAddress(dto);
        result.setCode(0);
        result.setMessage("设置成功");
        result.setSuccess(true);
        return result;
    }

}
