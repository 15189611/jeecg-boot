package org.jeecg.modules.mall.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.support.entity.Area;
import org.jeecg.modules.support.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @Description: 区域信息
* @Author: jeecg-boot
* @Date:   2019-07-04
* @Version: V1.0
*/
@Slf4j
@Api(tags="区域信息")
@RestController
@RequestMapping("/api/mall/area")
public class AreaApiController {
   @Autowired
   private IAreaService areaService;

   /**
    * 按父级代码查找 相关区域信息
    * @param area
    * @return
    */
   @PostMapping(value = "/queryListByParent")
   public Result<List<Area>> queryListByParent(Area area) {
       Result<List<Area>> result = new Result();
       List  pageList = areaService.queryAreaByParent(area.getParentCode());
       result.setSuccess(true);
       result.setCode(0);
       result.setResult(pageList);
       return result;
   }


   /**
     * 通过id查询
    * @param id
    * @return
    */
   @AutoLog(value = "区域信息-通过id查询")
   @ApiOperation(value="区域信息-通过id查询", notes="区域信息-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<Area> queryById(@RequestParam(name="id",required=true) String id) {
       Result<Area> result = new Result<Area>();
       Area area = areaService.getById(id);
       if(area==null) {
           result.error500("未找到对应实体");
       }else {
           result.setResult(area);
           result.setSuccess(true);
       }
       return result;
   }


}
