package com.trade.triuserservice.service;

import com.trade.triuserservice.domain.dto.LoginDTO;
import com.trade.triuserservice.domain.dto.UserRegisterDTO;
import com.trade.triuserservice.domain.vo.UserRegisterVO;

public interface UserService {
    
    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    UserRegisterVO register(UserRegisterDTO registerDTO);

    String login(LoginDTO loginDTO);

    void update(UserRegisterDTO userDTO);
}
