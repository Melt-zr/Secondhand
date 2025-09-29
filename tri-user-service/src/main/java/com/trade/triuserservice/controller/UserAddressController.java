package com.trade.triuserservice.controller;

import com.trade.triuserservice.domain.dto.UserAddressDTO;
import com.trade.triuserservice.domain.po.UserAddress;
import com.trade.triuserservice.domain.vo.ResultVO;
import com.trade.triuserservice.domain.vo.UserRegisterVO;
import com.trade.triuserservice.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    /*
    * 新增收货地址
    * @param UserAddressDTO 地址信息
    * @return 新增的地址
    * */
    @PostMapping()
    public ResultVO<String> addAddress(@Validated @RequestBody UserAddressDTO userAddressDTO) {
        try {
            userAddressService.addAddress(userAddressDTO);
            return ResultVO.success("添加成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("添加失败，请稍后重试");
        }
    }

    /*
    * 获取收货地址列表
    * @param userId 用户ID
    * @return 收货地址列表
    * */
    @GetMapping()
    public ResultVO<UserAddress[]> getAddresses() {
        try {
            UserAddress[] addresses = userAddressService.getAddresses();
            return ResultVO.success(addresses);
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("获取失败，请稍后重试");
        }
    }

    /*
    * 修改收货地址
    * @param addressId 地址ID
    * @param userAddressDTO 地址信息
    * @return 修改结果
    * */
    @PutMapping("/{id}")
    public ResultVO<String> updateAddress(@PathVariable("id") int addressId, @Validated @RequestBody UserAddressDTO userAddressDTO) {
        try {
            userAddressService.updateAddress(addressId, userAddressDTO);
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }

    /*
    * 删除收货地址
    * @param addressId 地址ID
    * @return 删除结果
    * */
    @DeleteMapping("/{id}")
    public ResultVO<String> deleteAddress(@PathVariable("id") int addressId) {
        try {
            userAddressService.deleteAddress(addressId);
            return ResultVO.success("删除成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("删除失败，请稍后重试");
        }
    }

    /*
    * 设置默认收货地址
    * @param addressId 地址ID
    * @return 设置结果
    * */
    @PutMapping("/default/{id}")
    public ResultVO<String> setDefaultAddress(@PathVariable("id") int addressId) {
        try {
            userAddressService.setDefaultAddress(addressId);
            return ResultVO.success("设置成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("设置失败，请稍后重试");
        }
    }
}
