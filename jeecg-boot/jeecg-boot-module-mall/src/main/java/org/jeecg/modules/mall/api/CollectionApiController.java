package org.jeecg.modules.mall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.mall.api.vo.ReqCancelCollected;
import org.jeecg.modules.mall.api.vo.ReqCollected;
import org.jeecg.modules.mall.config.MallConfig;
import org.jeecg.modules.mall.entity.Collection;
import org.jeecg.modules.mall.entity.bo.CollectionProductBO;
import org.jeecg.modules.mall.service.ICollectionService;
import org.jeecg.modules.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
* @Description: 我的收藏
* @Author: jeecg-boot
* @Date:   2019-06-27
* @Version: V1.0
*/
@Slf4j
@Api(tags="我的收藏")
@RestController
@RequestMapping("/api/mall/collection")
public class CollectionApiController {
   @Autowired
   private ICollectionService collectionService;

   @Autowired
   private IProductService productService;
    @Autowired
    private MallConfig mallConfig;

   /**
     * 分页列表查询
    * @param collection
    * @return
    */
   @AutoLog(value = "我的收藏-分页列表查询")
   @ApiOperation(value="我的收藏-分页列表查询", notes="我的收藏-分页列表查询")
   @PostMapping(value = "/list")
   public Result queryPageList(Collection collection) {
       Result<List<CollectionProductBO>> result = new Result<>();
       List<CollectionProductBO> pageList = collectionService.queryCollectionProductByUserId(collection.getUserId());
       pageList.forEach(e -> e.setPicUrl(mallConfig.getPicPrefix()+productService.getProductPic(e.getProductId())));
       result.setSuccess(true);
       result.setCode(0);
       result.setResult(pageList);
       return result;
   }

   /**
     *   添加
    * @param req
    * @return
    */

   @PostMapping(value = "/add")
   public Result add(ReqCollected req) {
       Result result = new Result<Collection>();
       try {

           Collection collection = collectionService.queryByUserIdAndProductId(req.getUserId(),req.getProductId());
           if(collection!=null){
               result.setCode(1);
               collectionService.removeById(collection.getId());
               result.setResult("取消成功！");
           }else {
               collection =new Collection();
               collection.setUserId(req.getUserId());
               collection.setProductId(req.getProductId());
               collectionService.save(collection);
               result.setCode(0);
               result.setResult("收藏成功！");
           }

       } catch (Exception e) {
           log.error(e.getMessage(),e);
           result.error500("操作失败");
       }
       return result;
   }


   /**
     *  通过id删除
    * @param req
    * @return
    */
   @PostMapping(value = "/remove")
   public Result delete(ReqCancelCollected req) {
       Result result = new Result();
       Collection collection = collectionService.getById(req.getId());
       if(collection==null) {
           result.error500("操作异常");
       }else {
           if(collection.getUserId().equals(req.getUserId())){
               collectionService.removeById(req.getId());
               result.setCode(0);
           }else {
                result.setCode(1);
                result.setMessage("删除不成功！");
           }
       }

       return result;
   }

   /**
     *  批量删除
    * @param ids
    * @return
    */
   @AutoLog(value = "我的收藏-批量删除")
   @ApiOperation(value="我的收藏-批量删除", notes="我的收藏-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<Collection> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       Result<Collection> result = new Result<Collection>();
       if(ids==null || "".equals(ids.trim())) {
           result.error500("参数不识别！");
       }else {
           this.collectionService.removeByIds(Arrays.asList(ids.split(",")));
           result.success("删除成功!");
       }
       return result;
   }

   /**
     * 通过productId和userId查询是否已收藏
    * @param req
    * @return
    */

   @PostMapping(value = "/queryCollected")
   public Result queryCollected(ReqCollected req) {
       Result result = new Result();
       Collection collection = collectionService.queryByUserIdAndProductId(req.getUserId(),req.getProductId());
       if(collection != null){
           result.setCode(0);
           result.setMessage("已收藏");
       }else{
           result.setCode(1);
           result.setMessage("未收藏");
       }
       return result;
   }

}
