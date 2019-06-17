package org.jeecg.modules.mall.api;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.WeChatUtils;
import org.jeecg.modules.mall.api.vo.AccountDetailVO;
import org.jeecg.modules.mall.entity.Banner;

import org.jeecg.modules.mall.entity.UserAccount;
import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.entity.UserDetail;
import org.jeecg.modules.mall.service.IUserAccountService;
import org.jeecg.modules.mall.service.IUserAuthService;
import org.jeecg.modules.mall.service.IUserDetailService;
import org.jeecg.modules.mall.vo.WeChatAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
* @Description:
* @Author: jeecg-boot
* @Date:   2019-06-015
* @Version: V1.0
*/
@Slf4j
@Api(tags="用户登录")
@RestController
@RequestMapping("/api/mall/wechat")
public class WeChatApiController {
    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IUserAuthService userAuthService;
    @Autowired
    private IUserDetailService userDetailService;

   /**
     * 小程序用户登录
    * @param weChatAuthInfo
    * @return
    */
   @ApiOperation(value="小程序用户登录", notes="小程序用户登录")
   @GetMapping(value = "/auth")
   public Result auth(WeChatAuthInfo weChatAuthInfo) {
       Result result = new Result<>();
       AccountDetailVO accountDetail = new AccountDetailVO();
       //用户登录
       UserAccount userAccount = null;
       if(weChatAuthInfo!=null){

           UserAuth userAuth = userAuthService.queryByOpenId(weChatAuthInfo.getOpenId());

           if(userAuth!=null && StringUtils.isEmpty(userAuth.getUserId())){

               //新增 一个用户
               userAccount = new UserAccount();

               userAccount.setNickName(weChatAuthInfo.getNickName());
               userAccount.setAvatar(weChatAuthInfo.getAvatarUrl());
               userAccount.setLocked(1);
               userAccount.setLastVisitTime(new Date());
               userAccount.setRegisterTime(new Date());

               UserDetail userDetail= new UserDetail();
               userDetail.setGender(weChatAuthInfo.getGender());
               userDetail.setAddress(weChatAuthInfo.getProvince()+weChatAuthInfo.getCity()+weChatAuthInfo.getCountry());
               userAccountService.userAuth(userAccount,userAuth,userDetail);
               accountDetail.setAvatar(userAccount.getAvatar());
               accountDetail.setNickName(userAccount.getNickName());


           }

       }

       result.setSuccess(true);

       return null;
   }




}
