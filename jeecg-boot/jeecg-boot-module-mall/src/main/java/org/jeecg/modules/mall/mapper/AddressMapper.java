package org.jeecg.modules.mall.mapper;


import org.jeecg.modules.mall.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 收获地址
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
public interface AddressMapper extends BaseMapper<Address> {

    Address queryUserDefaultAddress(String userId);

}
