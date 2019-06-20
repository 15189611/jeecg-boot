package org.jeecg.modules.mall.api;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.mall.api.vo.AccountDetailVO;
import org.jeecg.modules.mall.entity.UserAccount;
import org.jeecg.modules.mall.entity.UserAuth;
import org.jeecg.modules.mall.service.IUserAccountService;
import org.jeecg.modules.mall.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 用户信息
 * @Author: jeecg-boot
 * @Date: 2019-06-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/api/mall/userAccount")
@Slf4j
public class UserAccountApiController {
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserAuthService userAuthService;


    /**
     * 通过id查询
     *
     * @param openId
     * @return
     */
    @PostMapping(value = "/getUserInfo")
    public Result<AccountDetailVO> getUserInfo(@RequestParam(name = "id", required = true) String openId) {
        Result<AccountDetailVO> result = new Result<>();

        UserAuth auth = userAuthService.queryByOpenId(openId);
        if (auth == null) {
            //

        } else {
            UserAccount userAccount = userAccountService.getById(auth.getUserId());
            if (userAccount == null) {
                result.error500("未找到对应实体");
            } else {
                AccountDetailVO accountDetailVO = new AccountDetailVO();
                accountDetailVO.setUserId(auth.getId());
                accountDetailVO.setNickName(userAccount.getNickName());
                accountDetailVO.setOpenId(openId);
                accountDetailVO.setAvatar(userAccount.getAvatar());
                result.setResult(accountDetailVO);
                result.setSuccess(true);
            }
        }
        return result;
    }


}
