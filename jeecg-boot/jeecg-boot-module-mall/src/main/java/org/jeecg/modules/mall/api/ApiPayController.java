package org.jeecg.modules.mall.api;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.config.WeChatConfig;
import org.jeecg.common.util.*;
import org.jeecg.modules.mall.api.vo.ReqPrepay;
import org.jeecg.modules.mall.entity.Order;
import org.jeecg.modules.mall.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


@Slf4j
@RestController
@RequestMapping("/api/pay")
public class ApiPayController {
    @Autowired
    private IOrderService orderService;
//    @Autowired
//    private ApiOrderGoodsService orderGoodsService;
//    @Autowired
//    private RedisService redisService;


    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private WeChatUtils weChatUtils;

    /**
     * 获取支付的请求参数
     */
    @PostMapping("prepay")
    public Result payPrepay(ReqPrepay prepay , HttpServletRequest req) {
        //
        String ip = IPUtils.getIpAddr(req);
        if (StringUtils.isBlank(ip)) {
            ip = "8.8.8.8";
        }
        Order order = orderService.getById(prepay.getOrderId());
        if (null == order) {
            return Result.error(1, "订单不存在！");
        }
        if (order.getPayStatus() != 0) {
            return Result.error(1, "订单已支付，请不要重复操作");
        }
            String nonceStr = CharUtil.getRandomString(32);
            //https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3
            Map<Object, Object> resultObj = new TreeMap();

            try {
                Map<Object, Object> parame = new TreeMap();
                parame.put("appid", weChatConfig.getAppId());
                // 商家账号。
                parame.put("mch_id", weChatConfig.getMchId());
                String randomStr = CharUtil.getRandomNum(18).toUpperCase();
                // 随机字符串
                parame.put("nonce_str", randomStr);
                // 商户订单编号
                parame.put("out_trade_no", order.getOrderNo());
                // 商品描述
                parame.put("body", "超市-支付");

                //支付金额
                parame.put("total_fee", order.getRealAmount() * 100);
                // 回调地址
                parame.put("notify_url", weChatConfig.getNotifyUrl());
                // 交易类型APP
                parame.put("trade_type", weChatConfig.getTradeType());
                parame.put("spbill_create_ip", ip);
                parame.put("openid", prepay.getOpenId());
                String sign = weChatUtils.arraySign(parame, weChatConfig.getPaySignKey());
                // 数字签证
                parame.put("sign", sign);

                String xml = MapUtils.convertMap2Xml(parame);
                log.info("xml:" + xml);
                String weChatResp = WeChatUtils.requestOnce(weChatConfig.getUniformOrder(), xml);
                Map<String, Object> resultUn = XmlUtil.xmlStrToMap(weChatResp);
                // 响应报文
                String return_code = MapUtils.getString("return_code", resultUn);
                String return_msg = MapUtils.getString("return_msg", resultUn);
                //
                if (return_code.equalsIgnoreCase("FAIL")) {
                    log.error(return_msg);
                    return Result.error(1, "支付失败," + return_msg);
                } else if (return_code.equalsIgnoreCase("SUCCESS")) {
                    // 返回数据
                    String result_code = MapUtils.getString("result_code", resultUn);
                    String err_code_des = MapUtils.getString("err_code_des", resultUn);
                    if (result_code.equalsIgnoreCase("FAIL")) {
                        return Result.error(1, "支付失败," + err_code_des);
                    } else if (result_code.equalsIgnoreCase("SUCCESS")) {

                        String prepay_id = MapUtils.getString("prepay_id", resultUn);
                        // 先生成paySign 参考https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
                        resultObj.put("appId", weChatConfig.getAppId());
                        resultObj.put("timeStamp", Long.valueOf(System.currentTimeMillis()).toString());
                        resultObj.put("nonceStr",  CharUtil.getRandomString(32));
                        resultObj.put("package", "prepay_id=" + prepay_id);
                        resultObj.put("signType", "MD5");
                        String paySign = WeChatUtils.arraySign(resultObj, weChatConfig.getPaySignKey());
                        resultObj.put("paySign", paySign);
                        // 业务处理
                        // 付款中
                        Order orderDO = new Order();
                        orderDO.setPayStatus(1);//1付款中
                        orderDO.setId(order.getId());
                        orderService.updateById(order);
                        Result result =new Result();
                        result.setCode(0);
                        result.setSuccess(true);
                        result.setResult(resultObj);
                        return result;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error(1, "下单失败,error=" + e.getMessage());
            }
            return Result.error(1, "下单失败");
        }

//    /**
//     * 微信查询订单状态
//     */
//    @RequestMapping("query")
//    public Object orderQuery(@LoginUser UserVo loginUser, Integer orderId) {
//        if (orderId==null) {
//            return toResponsFail("订单不存在");
//        }
//
//        Map<Object, Object> parame = new TreeMap<Object, Object>();
//        parame.put("appid", ResourceUtil.getConfigByName("wx.appId"));
//        // 商家账号。
//        parame.put("mch_id", ResourceUtil.getConfigByName("wx.mchId"));
//        String randomStr = CharUtil.getRandomNum(18).toUpperCase();
//        // 随机字符串
//        parame.put("nonce_str", randomStr);
//        // 商户订单编号
//        parame.put("out_trade_no", orderId);
//
//        String sign = WechatUtil.arraySign(parame, ResourceUtil.getConfigByName("wx.paySignKey"));
//        // 数字签证
//        parame.put("sign", sign);
//
//        String xml = MapUtils.convertMap2Xml(parame);
//        logger.info("xml:" + xml);
//        Map<String, Object> resultUn = null;
//        try {
//            resultUn = XmlUtil.xmlStrToMap(WechatUtil.requestOnce(ResourceUtil.getConfigByName("wx.orderquery"), xml));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toResponsFail("查询失败,error=" + e.getMessage());
//        }
//        // 响应报文
//        String return_code = MapUtils.getString("return_code", resultUn);
//        String return_msg = MapUtils.getString("return_msg", resultUn);
//
//        if (return_code.equals("SUCCESS")) {
//            String trade_state = MapUtils.getString("trade_state", resultUn);
//            if (trade_state.equals("SUCCESS")) {
//                // 更改订单状态
//                // 业务处理
//                OrderVo orderInfo = new OrderVo();
//                orderInfo.setId(orderId);
//                orderInfo.setPay_status(2);
//                orderInfo.setOrder_status(201);
//                orderInfo.setShipping_status(0);
//                orderInfo.setPay_time(new Date());
//                orderService.update(orderInfo);
//                return toResponsMsgSuccess("支付成功");
//            } else if (trade_state.equals("USERPAYING")) {
//                // 重新查询 正在支付中
//                Integer num = redisService.get(OrderKey.queryRepeatNum(), orderId+"", Integer.class);
//                if (num==null) {
//                    redisService.set(OrderKey.queryRepeatNum(), orderId+"", 1);
//                    this.orderQuery(loginUser, orderId);
//                } else if (num <=3) {
//                    redisService.incr(OrderKey.queryRepeatNum(), orderId+"");
//                    this.orderQuery(loginUser, orderId);
//                } else {
//                    return toResponsFail("查询失败,error=" + trade_state);
//                }
//
//            } else  {
//                // 失败
//                return toResponsFail("查询失败,error=" + trade_state);
//            }
//        } else {
//            return toResponsFail("查询失败,error=" + return_msg);
//        }
//        return toResponsFail("查询失败，未知错误");
//    }

    /**
     * 微信订单回调接口
     *
     * @return
     */
    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            InputStream in = request.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            //xml数据
            String reponseXml = new String(out.toByteArray(), "utf-8");

//            WechatRefundApiResult result = (WechatRefundApiResult) XmlUtil.xmlStrToBean(reponseXml, WechatRefundApiResult.class);
//            String result_code = result.getResult_code();
//            if (result_code.equalsIgnoreCase("FAIL")) {
//                //订单编号
//                String out_trade_no = result.getOut_trade_no();
//                log.error("订单" + out_trade_no + "支付失败");
//                response.getWriter().write(setXml("SUCCESS", "OK"));
//            } else if (result_code.equalsIgnoreCase("SUCCESS")) {
//                //订单编号
//                String out_trade_no = result.getOut_trade_no();
//                log.error("订单" + out_trade_no + "支付成功");
//                // 业务处理
//                Order orderInfo = orderService.queryObject(Integer.valueOf(out_trade_no));
//                orderInfo.setPayStatus(2);//付款成功
//                orderInfo.setStatus(3);//待发货
//                orderService.updateById(orderInfo);
//                response.getWriter().write(setXml("SUCCESS", "OK"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

//    /**
//     * 订单退款请求
//     */
//    @RequestMapping("refund")
//    public Object refund(@LoginUser UserVo loginUser, Integer orderId) {
//        //
//        OrderVo orderInfo = orderService.queryObject(orderId);
//
//        if (null == orderInfo) {
//            return toResponsObject(400, "订单已取消", "");
//        }
//
//        if (orderInfo.getOrder_status() == 401 || orderInfo.getOrder_status() == 402) {
//            return toResponsObject(400, "订单已退款", "");
//        }
//
////        if (orderInfo.getPay_status() != 2) {
////            return toResponsObject(400, "订单未付款，不能退款", "");
////        }
//
////        WechatRefundApiResult result = WechatUtil.wxRefund(orderInfo.getId().toString(),
////                orderInfo.getActual_price().doubleValue(), orderInfo.getActual_price().doubleValue());
//        WechatRefundApiResult result = WechatUtil.wxRefund(orderInfo.getId().toString(),
//                10.01, 10.01);
//        if (result.getResult_code().equals("SUCCESS")) {
//            if (orderInfo.getOrder_status() == 201) {
//                orderInfo.setOrder_status(401);
//            } else if (orderInfo.getOrder_status() == 300) {
//                orderInfo.setOrder_status(402);
//            }
//            orderService.update(orderInfo);
//            return toResponsObject(400, "成功退款", "");
//        } else {
//            return toResponsObject(400, "退款失败", "");
//        }
//    }
    //返回微信服务
    public static String setXml(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

}