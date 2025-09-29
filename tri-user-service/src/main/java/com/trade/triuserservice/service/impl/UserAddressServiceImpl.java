package com.trade.triuserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triuserservice.domain.dto.UserAddressDTO;
import com.trade.triuserservice.domain.po.UserAddress;
import com.trade.triuserservice.mapper.UserAddressMapper;
import com.trade.triuserservice.service.UserAddressService;
import com.trade.triuserservice.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Override
    public void addAddress(UserAddressDTO userAddressDTO) {
        String userId = UserContext.getCurrentUserId();
        System.out.println("userId: " + userId);
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(Integer.parseInt(userId));
        userAddress.setReceiverName(userAddressDTO.getReceiverName());
        userAddress.setReceiverPhone(userAddressDTO.getReceiverPhone());
        userAddress.setProvince(userAddressDTO.getProvince());
        userAddress.setCity(userAddressDTO.getCity());
        userAddress.setDistrict(userAddressDTO.getDistrict());
        userAddress.setDetailAddress(userAddressDTO.getDetailAddress());
        userAddress.setIsDefault(0);

        this.save(userAddress);
    }

    @Override
    public UserAddress[] getAddresses() {
        String userId = UserContext.getCurrentUserId();

        return this.list(new QueryWrapper<UserAddress>().eq("user_id", userId)).toArray(new UserAddress[0]);
    }

    @Override
    public void updateAddress(int addressId, UserAddressDTO userAddressDTO) {
        UserAddress userAddress = new UserAddress();
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", addressId);
        userAddress.setReceiverName(userAddressDTO.getReceiverName());
        userAddress.setReceiverPhone(userAddressDTO.getReceiverPhone());
        userAddress.setProvince(userAddressDTO.getProvince());
        userAddress.setCity(userAddressDTO.getCity());
        userAddress.setDistrict(userAddressDTO.getDistrict());
        userAddress.setDetailAddress(userAddressDTO.getDetailAddress());
        this.update(userAddress, queryWrapper);
    }

    @Override
    public void deleteAddress(int addressId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", addressId);
        this.remove(queryWrapper);
    }

    @Transactional
    @Override
    public void setDefaultAddress(int addressId) {
        // 将该用户的所有地址设置为非默认
        QueryWrapper<UserAddress> updateWrapper = new QueryWrapper<>();
        System.out.println("userId: " + UserContext.getCurrentUserId());
        updateWrapper.eq("user_id", UserContext.getCurrentUserId());
        UserAddress nonDefaultAddress = new UserAddress();
        nonDefaultAddress.setIsDefault(0);
        this.update(nonDefaultAddress, updateWrapper);

        // 将指定地址设置为默认
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", addressId);
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(1);
        this.update(userAddress, queryWrapper);
    }
}
