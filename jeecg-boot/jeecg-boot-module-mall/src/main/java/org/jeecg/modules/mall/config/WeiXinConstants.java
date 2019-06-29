package org.jeecg.modules.mall.config;

/**
 * 
 * @author chinasoft
 *
 */
public interface WeiXinConstants {
    String PAY_ORG = "WEIXIN";

    /**
     * 微信退款，支付成功
     */
    public static final String SUCCESS ="SUCCESS";
    /**
     * 微信退款失败
     */
    public static final String FAIL ="FAIL";
    /**
     * 微信退款处理中
     */
    public static final String PROCESSING ="PROCESSING";
    /**
     * 微信退款未确定，需要商户原退款单号重新发起 
     */
    public static final String NOTSURE ="NOTSURE";
    
    /**
     * 微信退款转入代发，退款到银行发现用户的卡作废或者冻结了，
     * 导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
     */
    public static final String CHANGE ="CHANGE";
    
    /**
     * 微信订单转入退款
     */
    public static final String REFUND ="REFUND";
    /**
     * 微信订单未支付
     */
    public static final String NOTPAY ="NOTPAY";
    /**
     * 微信订单已关闭
     */
    public static final String CLOSED ="CLOSED";
    /**
     * 微信订单已撤销
     */
    public static final String REVOKED ="REVOKED";
    
    /**
     * 微信订单处理中
     */
    public static final String USERPAYING ="USERPAYING";
    /**
     * 微信订单支付失败(其他原因，如银行返回失败)
     */
    public static final String PAYERROR ="PAYERROR";
    
}
