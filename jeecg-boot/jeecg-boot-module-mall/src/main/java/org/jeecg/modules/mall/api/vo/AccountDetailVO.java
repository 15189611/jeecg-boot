package org.jeecg.modules.mall.api.vo;

import lombok.Data;

@Data
public class AccountDetailVO {

    private String userId;
    /**用户名*/
    private String userName;
    /**昵称*/
    private String nickName;
    /**头像*/
    private String avatar;
    private String openId;
}
