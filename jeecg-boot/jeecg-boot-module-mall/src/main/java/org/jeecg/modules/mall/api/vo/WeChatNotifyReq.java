package org.jeecg.modules.mall.api.vo;

import lombok.Data;

/**
 * 微信支付通知参数封装类
 */
@Data
public class WeChatNotifyReq {
	private String return_code;
	private String return_msg;

	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String result_code;
	private String err_code;
	private String err_code_des;
	private String openid;
	private String is_subscribe;
	private String trade_type;
	private String bank_type;
	private String total_fee;
	private String settlement_total_fee;
	private String fee_type;
	private String cash_fee;
	private String cash_refund_fee;
	private String refund_status;
	private String transaction_id;
	private String out_trade_no;

	
}
