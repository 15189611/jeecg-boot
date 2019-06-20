package org.jeecg.modules.mall.api;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.WeChatUtils;
import org.jeecg.modules.mall.api.vo.AccountDetailVO;

import org.jeecg.modules.mall.entity.UserAccount;
import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.entity.UserDetail;
import org.jeecg.modules.mall.service.IUserAccountService;
import org.jeecg.modules.mall.service.IUserAuthService;
import org.jeecg.modules.mall.vo.WeChatAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * @Description:
 * @Author: jeecg-boot
 * @Date: 2019-06-015
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "用户登录")
@RestController
@RequestMapping("/api/mall/wechat")
public class WeChatApiController {
    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IUserAuthService userAuthService;

    /**
     * 小程序用户登录
     *
     * @param weChatAuthInfo
     * @return
     */
    @ApiOperation(value = "小程序用户登录", notes = "小程序用户登录")
    @GetMapping(value = "/auth")
    public Result auth(WeChatAuthInfo weChatAuthInfo) {
        Result result = new Result<>();
        AccountDetailVO accountDetail = new AccountDetailVO();
        //用户登录
        UserAccount userAccount = null;
        UserAuth userAuth = null;
        if (weChatAuthInfo != null) {

            Map<String, String> map = WeChatUtils.getOpenId(weChatAuthInfo.getCode());

            if (map != null) {
                String openId = map.get("openid");
                String sessionKey = map.get("session_key");
                userAuth = userAuthService.queryByOpenId(openId);
                if(userAuth == null){
                    //新增 一个用户
                    userAccount = new UserAccount();

                    userAccount.setNickName(weChatAuthInfo.getNickName());
                    userAccount.setAvatar(weChatAuthInfo.getAvatarUrl());
                    userAccount.setLocked(1);
                    userAccount.setLastVisitTime(new Date());
                    userAccount.setRegisterTime(new Date());

                    UserDetail userDetail = new UserDetail();
                    userDetail.setGender(weChatAuthInfo.getGender());
                    userDetail.setAddress(weChatAuthInfo.getProvince() + weChatAuthInfo.getCity() + weChatAuthInfo.getCountry());

                    userAuth = new UserAuth();
                    userAuth.setOpenId(openId);
                    userAuth.setIdentityType(1);
                    userAuth.setCredential(sessionKey);
                    userAccountService.userAuth(userAccount, userAuth, userDetail);
                }else{
                    userAccount = userAccountService.getById(userAuth.getUserId());
                    accountDetail.setAvatar(userAccount.getAvatar());
                    accountDetail.setNickName(userAccount.getNickName());
                    accountDetail.setUserId(userAccount.getId());
                }
            }
        }
        result.setResult(accountDetail);
        result.setSuccess(true);

        return result;
    }


}
