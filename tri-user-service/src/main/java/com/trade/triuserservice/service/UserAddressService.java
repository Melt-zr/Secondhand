package com.trade.triuserservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.triuserservice.domain.dto.UserAddressDTO;
import com.trade.triuserservice.domain.po.UserAddress;

public interface UserAddressService extends IService<UserAddress> {
    void addAddress(UserAddressDTO userAddressDTO);

    UserAddress[] getAddresses();

    void updateAddress(int addressId, UserAddressDTO userAddressDTO);

    void deleteAddress(int addressId);

    void setDefaultAddress(int addressId);
}
