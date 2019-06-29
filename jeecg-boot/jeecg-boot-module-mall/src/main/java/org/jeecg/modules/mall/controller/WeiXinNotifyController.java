//package org.jeecg.modules.mall.controller;
//
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.DigestUtils;
////import org.jeecg.common.util.ParseXMLHelper;
//import org.jeecg.modules.mall.config.WeiXinAccountInfo;
//import org.jeecg.modules.mall.config.WeiXinConstants;
//import org.jeecg.modules.mall.vo.ReqWeiXinPayNotifyVO;
//import org.jeecg.modules.mall.vo.WeiXinPayNotifyResp;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//
///**
// * h5pay 接收支付成功异步通知
// *
// *
// */
//@Slf4j
//@Controller
//@RequestMapping("/weixin/h5pay")
//public class WeiXinNotifyController {
//    @Resource
//    private WeiXinAccountManager weixinAccountManager;
//    @Resource
//    private PayLifeCycle payLifeCycle;
//
//    @RequestMapping("/notify/{sellerId}")
//    @ResponseBody
//    public String notify(@RequestBody String Xml, @PathVariable("sellerId") String sellerId) {
//        ReqWeiXinPayNotifyVO notify_data = ParseXMLHelper.parseXMLToObject(Xml, ReqWeiXinPayNotifyVO.class);
//        String returnXml = null;
//        WeiXinPayNotifyResp weixinPayNotifyResp = new WeiXinPayNotifyResp();
//        log.info("收到微信H5支付后台支付成功通知内容:{}",Xml);
//        // 通过商户对象获得商户密钥信息
//        WeiXinAccountInfo weixinAccountInfo = new WeiXinAccountInfo();
//        // 对签名进行校验，成功后再执行业务逻辑代码
//        boolean check = checkSignNotify(notify_data, weixinAccountInfo);
//        if (check) {
//            // 获得payId及tradeNo信息，在notifyReceiver.onPaySuccess方法中用于参数传递
//            String payId = notify_data.getOut_trade_no();
//            String tradeNo = notify_data.getTransaction_id();
//            log.info("收到微信H5支付后台支付成功通知payId:{},支付状态：{}", payId, notify_data.getResult_code());
//            if (notify_data.getResult_code().equals("FAIL")) {
//                log.error("交易状态：{}---交易失败！", notify_data.getResult_code());
//            } else {
//                // 订单存在，是否已经发送过内部通知
//                int amt = notify_data.getTotal_fee();
//                // 获得支付完成时间转化为时，分，秒，毫秒的格式
//                Date orderPayTime = DateTimeUtil.parse(notify_data.getTime_end(), false);
//                try {
//                    // 将在notifyReceiver.onPaySuccess方法中更新订单状态,并新增通知记录
//                    int payStatus = PayStatus.PAY_SUCCESS;
//                    int type = NotifyType.BACK;
//                    // 启动延时通知
//                    payLifeCycle.onPaySuccess(payId, tradeNo, amt, orderPayTime, payStatus, type);
//                } catch (Exception e) {
//                    // 无支付记录抛异常
//                    log.error("SellerId:{},payId:{}----未找到支付记录!", sellerId, notify_data.getOut_trade_no());
//                    throw new BusinessException(SystemErrorCode.MMPG_NO_PAYINFO, ErrorCodeUtil.getErrorMsg(SystemErrorCode.MMPG_NO_PAYINFO));
//                }
//                log.info("发送通知成功:payId：{}--启动延时通知线程", payId);
//                weixinPayNotifyResp.setReturn_code(WeiXinConstants.SUCCESS);
//                weixinPayNotifyResp.setReturn_msg("");
//                returnXml = ParseXMLHelper.parseObjectToXML(weixinPayNotifyResp, WeiXinPayNotifyResp.class);
//            }
//        } else {
//            log.error("SellerId:{},payId:{}----签名验证失败!", sellerId, notify_data.getOut_trade_no());
//            weixinPayNotifyResp.setReturn_code(WeiXinConstants.FAIL);
//            weixinPayNotifyResp.setReturn_msg("签名失败");
//            returnXml = ParseXMLHelper.parseObjectToXML(weixinPayNotifyResp, WeiXinPayNotifyResp.class);
//        }
//        return returnXml;
//    }
//
//    /**
//     * 签名验证weixinPayNotifyData
//     *
//     * @param notifyData
//     * @param weixinAccountInfo
//     * @return
//     */
//    private boolean checkSignNotify(ReqWeiXinPayNotifyVO notifyData, WeiXinAccountInfo weixinAccountInfo) {
//        String preCheck = RequestParam.createLinkString(BeanUtil.transBeanToMap(weixinPayNotifyData, false), false,
//                false, "sign");
//        String SignTemp = preCheck + "&key=" + weixinAccountInfo.getApiSecret();
//
//        String sign = DigestUtils.md5Hex(SignTemp).toUpperCase();
//        if (sign.equals(weixinPayNotifyData.getSign())) {
//            return true;
//        }
//        return false;
//    }
//
//}
