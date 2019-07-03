package org.jeecg.modules.mall.api;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.SerialNo;
import org.jeecg.modules.mall.api.vo.*;
import org.jeecg.modules.mall.config.MallConfig;
import org.jeecg.modules.mall.entity.*;
import org.jeecg.modules.mall.entity.bo.CartProductBO;
import org.jeecg.modules.mall.entity.bo.OrderProductBO;
import org.jeecg.modules.mall.service.IAddressService;
import org.jeecg.modules.mall.service.ICartService;
import org.jeecg.modules.mall.service.IOrderProductService;
import org.jeecg.modules.mall.service.IOrderService;
import org.jeecg.modules.mall.vo.OrderPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
* @Description: 订单信息
* @Author: jeecg-boot
* @Date:   2019-06-22
* @Version: V1.0
*/
@RestController
@RequestMapping("/api/mall/order")
@Slf4j
public class OrderApiController {
   @Autowired
   private IOrderService orderService;
   @Autowired
   private IOrderProductService orderProductService;
   @Autowired
   private IAddressService addressService;
   @Autowired
   private ICartService cartService;
   @Autowired
   private MallConfig mallConfig;

   /**
     * 分页列表查询
    * @param order
    * @param pageNo
    * @param pageSize
    * @return
    */
   @PostMapping(value = "/list")
   public Result<List<OrderInfoVO>> queryPageList(Order order,
                                     @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                     @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
       Result<List<OrderInfoVO>> result = new Result<>();

       Page<Order> page = new Page<>(pageNo, pageSize);
       IPage<Order> pageList;
       if(order.getStatus() ==null) {
           //查看所有
           pageList = orderService.queryAllOrderByUserId(page, order.getUserId());
       }else{
           //查看指定
           pageList = orderService.queryOrderByUserIdAndStatus(page, order.getUserId(),order.getStatus());
       }
       result.setSuccess(true);

       List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
       for(Order orderDO : pageList.getRecords()) {
           OrderInfoVO orderInfoVO = new OrderInfoVO();
           BeanUtils.copyProperties(orderDO, orderInfoVO);
           //加载订单商品信息
           List<OrderProductBO> orderProductBOList = orderProductService.selectByMainId(orderDO.getId());
           orderProductBOList.forEach(e->e.setPicUrl(mallConfig.getPicPrefix()+e.getPicUrl()));
           orderInfoVO.setProductList(orderProductBOList);
           orderInfoVOList.add(orderInfoVO);
       }

       result.setResult(orderInfoVOList);
       return result;
   }

   /**
    *   添加
    * @param addOrder
    * @return
    */
   @PostMapping(value = "/add")
   public Result<OrderInfoVO> add(AddOrderVO addOrder) {
       Result<OrderInfoVO> result = new Result();

       try {
           String productIdDec= URLDecoder.decode(addOrder.getProductId(),"UTF-8");
           String ss = JSON.parseObject(productIdDec,String.class);
           addOrder.setProductId(ss);
       } catch (UnsupportedEncodingException e) {
           log.error("",e);
       }
       OrderInfoVO orderInfoVO = new OrderInfoVO();
       try {
           Order order = new Order();
           order.setUserId(addOrder.getUserId());
           Address address = addressService.queryUserDefaultAddress(addOrder.getUserId());
           if(address != null){
               order.setConsignee(address.getConsignee());
               order.setAddress("");
               order.setMobile(address.getMobile());
           }
           int orderAmount = 0;

           String productId = addOrder.getProductId();
           List<String> cartProductIds = new ArrayList<>();

           String[] productIds = productId.split(",");
           List<CartProductBO> cartProduct = cartService.queryListByUserIdAndIds(addOrder.getUserId(),Arrays.asList(productIds));
           List<OrderProduct> itemList = new ArrayList();
           for (CartProductBO bo:cartProduct){
               OrderProduct orderProduct = new OrderProduct();
               orderProduct.setProductId(bo.getProductId());
               orderProduct.setNum(bo.getNum());
               orderProduct.setProductPrice(bo.getSellingPrice());
               itemList.add(orderProduct);
               int productAmount = bo.getNum()*bo.getSellingPrice();
               orderAmount+=productAmount;
               cartProductIds.add(bo.getId());
           }

           order.setTotalAmount(orderAmount);
           order.setDiscountAmount(0);
           order.setRealAmount(order.getTotalAmount()-order.getDiscountAmount());
           order.setOrderTime(new Date());
           order.setOrderNo(SerialNo.getNo());
           order.setStatus(0);
           order.setPayStatus(0);
           String orderId = orderService.saveMain(order,itemList);
//           cartService.removeByIds(cartProductIds);
           result.success("添加成功！");
           //重新加载数据库订单数据
           Order orderDO =  orderService.getById(orderId);
           BeanUtils.copyProperties(orderDO, orderInfoVO);
           //加载订单商品信息
           List<OrderProductBO> orderProductBOList = orderProductService.selectByMainId(orderId);
           orderProductBOList.forEach(e-> e.setPicUrl(mallConfig.getPicPrefix()+e.getPicUrl()));
           orderInfoVO.setProductList(orderProductBOList);

           result.setResult(orderInfoVO);
           result.setCode(0);
           result.setSuccess(true);
       } catch (Exception e) {
           log.error(e.getMessage(),e);
           result.error500("操作失败");
       }
       return result;
   }

    /**
     * 获取订单支付详细信息
     * @param req
     * @return
     */
    @PostMapping(value = "/getOrderPayInfo")
    public Result<RespGetOrderPayInfoVO> getOrderPayInfo(ReqGetOrderPayInfoVO req) {
        Result<RespGetOrderPayInfoVO> result = new Result();

        try {
            //重新加载数据库订单数据
            RespGetOrderPayInfoVO resp =  new RespGetOrderPayInfoVO();
            Order orderDO =  orderService.getById(req.getOrderId());
            BeanUtils.copyProperties(orderDO, resp);
            //加载订单商品信息
            resp.setPayAmount(resp.getRealAmount());
            result.setResult(resp);
            result.setCode(0);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 小程序用户登录
     *
     * @param orderConfirm
     * @return
     */
    @ApiOperation(value = "确认订单", notes = "确认订单")
    @PostMapping(value = "/confirm")
    public Result auth(OrderConfirmVO orderConfirm) {
        Result result = new Result<>();
        Order order = orderService.getById(orderConfirm.getOrderId());

        if(order==null){
           //订单不存在
            result.setCode(1);
            return result;
        }else{
            if(!order.getUserId().equals(orderConfirm.getUserId())){
                //订单异常
                result.setCode(1);
                return result;
            }
        }
        if(!order.getRealAmount().equals(orderConfirm.getPayPrice())){
            result.setCode(1);
            return result;
        }
        Order orderDO = new Order();
        orderDO.setId(orderConfirm.getOrderId());
        orderDO.setRemark(orderConfirm.getRemark());
        orderDO.setStatus(1);//代付款订单
        orderDO.setUpdateTime(new Date());
        orderService.updateById(orderDO);
        Map detail = new HashMap();
        detail.put("orderNo",order.getOrderNo());
        detail.put("orderId",orderDO.getId());
        result.setResult(detail);
        result.setSuccess(true);

        return result;
    }



   /**
     *  编辑
    * @param orderPage
    * @return
    */
   @PutMapping(value = "/edit")
   public Result<Order> edit(@RequestBody OrderPage orderPage) {
       Result<Order> result = new Result<Order>();
       Order order = new Order();
       BeanUtils.copyProperties(orderPage, order);
       Order orderEntity = orderService.getById(order.getId());
       if(orderEntity==null) {
           result.error500("未找到对应实体");
       }else {
           orderService.updateById(order);
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
   public Result<Order> delete(@RequestParam(name="id",required=true) String id) {
       Result<Order> result = new Result<Order>();
       Order order = orderService.getById(id);
       if(order==null) {
           result.error500("未找到对应实体");
       }else {
           orderService.delMain(id);
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
   public Result<Order> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       Result<Order> result = new Result<Order>();
       if(ids==null || "".equals(ids.trim())) {
           result.error500("参数不识别！");
       }else {
           this.orderService.removeByIds(Arrays.asList(ids.split(",")));
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
   public Result<Order> queryById(@RequestParam(name="id",required=true) String id) {
       Result<Order> result = new Result<Order>();
       Order order = orderService.getById(id);
       if(order==null) {
           result.error500("未找到对应实体");
       }else {
           result.setResult(order);
           result.setSuccess(true);
       }
       return result;
   }

   //===========================以下是子表信息操作相关API====================================

   /**
     * 通过主表id查询订单商品
    * @param mainId
    * @return
    */
   @GetMapping(value = "/listOrderProductByMainId")
   public Result<List<OrderProduct>> queryOrderProductListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
       Result<List<OrderProduct>> result = new Result<List<OrderProduct>>();
       List<OrderProduct> orderProductList = null;
       if (mainId != null) {
//           orderProductList = orderProductService.selectByMainId(mainId);
           result.setResult(orderProductList);
           result.setSuccess(true);
           return result;
       }else return null;
   }

   /**
    * 添加订单商品
    *
    * @param orderProduct
    * @return
    */
   @PostMapping(value = "/addOrderProduct")
   public Result<OrderProduct> addOrderProduct(@RequestBody OrderProduct orderProduct) {
       Result<OrderProduct> result = new Result<>();
       try {
           boolean ok = orderProductService.save(orderProduct);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("添加订单商品成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("添加订单商品失败!");
           }
           return result;
       } catch (Exception e) {
           e.fillInStackTrace();
           result.setSuccess(false);
           result.setMessage("添加订单商品过程中出现了异常: " + e.getMessage());
           return result;
       }
   }

   /**
    * 编辑订单商品
    *
    * @param orderProduct
    * @return
    */
   @PutMapping("/editOrderProduct")
   public Result<OrderProduct> editOrderProduct(@RequestBody OrderProduct orderProduct) {
       Result<OrderProduct> result = new Result<>();
       try {
           boolean ok = orderProductService.updateById(orderProduct);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("更新订单商品成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("更新订单商品失败!");
           }
           return result;
       } catch (Exception e) {
           result.setSuccess(false);
           result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
           return result;
       }
   }

   /**
    * 通过id删除订单商品
    *
    * @param id
    * @return
    */
   @DeleteMapping(value = "/deleteOrderProduct")
   public Result<OrderProduct> deleteOrderProduct(@RequestParam(name = "id", required = true) String id) {
       Result<OrderProduct> result = new Result<>();
       try {
           boolean ok = orderProductService.removeById(id);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("删除订单商品成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("删除订单商品失败!");
           }
           return result;
       } catch (Exception e) {
           result.setSuccess(false);
           result.setMessage("删除订单商品过程中出现异常啦: " + e.getMessage());
           return result;
       }
   }

   /**
    * 批量删除订单商品
    *
    * @param ids
    * @return
    */
   @DeleteMapping(value = "/deleteBatchOrderProduct")
   public Result<OrderProduct> deleteBatchOrderProduct(@RequestParam(name = "ids", required = true) String ids) {
       Result<OrderProduct> result = new Result<OrderProduct>();
       if (ids == null || "".equals(ids.trim())) {
           result.error500("参数不识别！");
       } else {
           this.orderProductService.removeByIds(Arrays.asList(ids.split(",")));
           result.success("删除成功!");
       }
       return result;
   }


}
