package com.trade.triuserservice.controller;

import com.trade.triuserservice.domain.dto.UserAddressDTO;
import com.trade.triuserservice.domain.po.UserAddress;
import com.trade.triuserservice.domain.vo.ResultVO;
import com.trade.triuserservice.domain.vo.UserRegisterVO;
import com.trade.triuserservice.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
@Tag(name = "收货地址", description = "用户收货地址相关接口")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    /*
    * 新增收货地址
    * @param UserAddressDTO 地址信息
    * @return 新增的地址
    * */
    @Operation(summary = "新增收货地址", description = "新增用户收货地址")
    @PostMapping("/add")
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
    @Operation(summary = "获取收货地址列表", description = "获取用户收货地址列表")
    @GetMapping("/getall")
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
    @Operation(summary = "修改收货地址", description = "修改用户收货地址")
    @PutMapping("/update/{id}")
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
    @Operation(summary = "删除收货地址", description = "删除用户收货地址")
    @DeleteMapping("/delete/{id}")
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
    @Operation(summary = "设置默认收货地址", description = "设置用户默认收货地址")
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
