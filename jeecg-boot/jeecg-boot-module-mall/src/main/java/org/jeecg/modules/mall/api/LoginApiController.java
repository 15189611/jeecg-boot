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
import org.jeecg.modules.mall.service.IUserDetailService;
import org.jeecg.modules.mall.vo.WeChatAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/mall/login")
public class LoginApiController {
    @Autowired
    private IUserAuthService userAuthService;
    @Autowired
    private IUserAccountService userAccountService;

   /**
     * 小程序用户登录
    * @param code
    * @return
    */
   @ApiOperation(value="小程序用户登录", notes="小程序用户登录")
   @GetMapping(value = "/login")
   public Result login(String code) {
       Result result = new Result<>();
       result.setSuccess(false);
       AccountDetailVO accountDetail = new AccountDetailVO();

       //用户登录
       Map<String,String> map = WeChatUtils.getOpenId(code);
       UserAuth userAuth=null;
       if(map!=null){
           String openId =  map.get("openid");
           userAuth = userAuthService.queryByOpenId(openId);
           if(userAuth!=null){
               UserAccount account = userAccountService.getById(userAuth.getUserId());
               accountDetail.setAvatar(account.getAvatar());
               accountDetail.setNickName(account.getNickName());
               accountDetail.setUserId(account.getId());
               accountDetail.setOpenId(userAuth.getOpenId());
               result.setSuccess(true);
               result.setResult(accountDetail);
           }
       }
       result.setResult(accountDetail);

       return result;
   }




}
