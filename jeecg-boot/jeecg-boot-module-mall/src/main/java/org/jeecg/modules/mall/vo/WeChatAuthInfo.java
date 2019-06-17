package org.jeecg.modules.mall.vo;

import lombok.Data;

@Data
public class WeChatAuthInfo {

    private String openId;
    private String avatarUrl;
    private String city;
    private String country;
    private Integer gender;
    private String language;
    private String nickName;
    private String province;

}
