package com.trade.triuserservice.controller;


import com.trade.triuserservice.domain.dto.LoginDTO;
import com.trade.triuserservice.domain.dto.UserRegisterDTO;
import com.trade.triuserservice.domain.vo.ResultVO;
import com.trade.triuserservice.domain.vo.UserRegisterVO;
import com.trade.triuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册结果
     */
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

    /*
    * 用户登录
    * @param loginDTO 登录信息
    * @return 登录结果
    *
    * */
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

    /*
    * 用户资料修改
    * @param userDTO 修改信息
    * @return 修改结果
    *
    * */
    @PostMapping("/update")
    public ResultVO<String> update(@Validated @RequestBody ) {
        try {
            userService.update();
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }
}
