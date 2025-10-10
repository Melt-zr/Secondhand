package com.trade.triuserservice.controller;


import com.trade.triuserservice.domain.dto.LoginDTO;
import com.trade.triuserservice.domain.dto.UserRegisterDTO;
import com.trade.triuserservice.domain.dto.UserUpdateDTO;
import com.trade.triuserservice.domain.po.User;
import com.trade.triuserservice.domain.vo.ResultVO;
import com.trade.triuserservice.domain.vo.UserRegisterVO;
import com.trade.triuserservice.service.UserService;
import com.trade.triuserservice.utils.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/register")
    public ResultVO<UserRegisterVO> register(@Validated @RequestBody UserRegisterDTO registerDTO) {
        try {
            UserRegisterVO registerVO = userService.register(registerDTO);
            return ResultVO.success("注册成功", registerVO);
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("注册失败，请稍后重试");
        }
    }

    /**
    * 用户登录
    * @param loginDTO 登录信息
    * @return 登录结果
    *
    * */
    @Operation(summary = "用户登录", description = "用户登录")
    @PostMapping("/login")
    public ResultVO<String> login(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            String token = userService.login(loginDTO);
            return ResultVO.success("登录成功", token);
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("登录失败，请稍后重试");
        }
    }

    /**
    * 用户资料修改
    * @param userUpdateDTO 修改信息
    * @return 修改结果
    *
    * */
    @Operation(summary = "用户资料修改", description = "修改用户资料")
    @PutMapping("/update")
    public ResultVO<String> update(@Validated @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            userService.update(userUpdateDTO);
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }

}
