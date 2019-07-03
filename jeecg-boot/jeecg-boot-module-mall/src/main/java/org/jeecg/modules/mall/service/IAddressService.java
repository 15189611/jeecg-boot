package org.jeecg.modules.mall.service;

import org.jeecg.modules.mall.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 收获地址
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
public interface IAddressService extends IService<Address> {


    Address queryUserDefaultAddress(String userId);
}
