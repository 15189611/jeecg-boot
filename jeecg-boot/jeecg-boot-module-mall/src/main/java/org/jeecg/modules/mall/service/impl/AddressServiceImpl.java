package org.jeecg.modules.mall.service.impl;

import org.jeecg.modules.mall.entity.Address;
import org.jeecg.modules.mall.mapper.AddressMapper;
import org.jeecg.modules.mall.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 收获地址
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Address queryUserDefaultAddress(String userId) {
        return addressMapper.queryUserDefaultAddress(userId);
    }
}
