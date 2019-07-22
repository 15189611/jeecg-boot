package org.jeecg.modules.mall.util;

/**
 * 商城常量类
 */
public class MallConstant {

    public enum OrderStatus{

        PAYING(1,"待支付"),
        PAYED(2,"待发货"),
        DD(3,"待收获"),
        FINISH(4,"已完成");
        private int code;
        private String value;


        OrderStatus(int code,String value){
            this.code=code;
            this.value=value;
        }

        public int getCode(){
            return this.code;
        }

        public static String getValue(int code){
            for(OrderStatus e:values()){
                if(e.getCode()==code){
                    return e.value;
                }
            }
            return "";
        }
    }
}
