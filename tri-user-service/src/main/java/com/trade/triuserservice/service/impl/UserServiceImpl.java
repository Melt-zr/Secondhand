package com.trade.triuserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triuserservice.domain.dto.LoginDTO;
import com.trade.triuserservice.domain.dto.UserRegisterDTO;
import com.trade.triuserservice.domain.dto.UserUpdateDTO;
import com.trade.triuserservice.domain.po.User;
import com.trade.triuserservice.domain.vo.UserRegisterVO;
import com.trade.triuserservice.mapper.UserMapper;
import com.trade.triuserservice.service.UserService;
import com.trade.triuserservice.utils.JwtUtil;
import com.trade.triuserservice.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserRegisterVO register(UserRegisterDTO registerDTO) {
        // 检查账号是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", registerDTO.getAccount());
        User existingUser = this.getOne(queryWrapper);
        if (existingUser != null) {
            throw new RuntimeException("账号已存在");
        }

        // 检查手机号是否已存在（如果提供了手机号）
        if (StringUtils.hasText(registerDTO.getPhone())) {
            QueryWrapper<User> phoneQueryWrapper = new QueryWrapper<>();
            phoneQueryWrapper.eq("phone", registerDTO.getPhone());
            User existingPhoneUser = this.getOne(phoneQueryWrapper);
            if (existingPhoneUser != null) {
                throw new RuntimeException("手机号已被注册");
            }
        }

        // 创建新用户
        User user = new User();
        user.setUserName(registerDTO.getUserName());
        user.setAccount(registerDTO.getAccount());

        // 直接存储明文密码
        user.setPassword(registerDTO.getPassword());

        user.setPhone(registerDTO.getPhone());
        user.setGender(registerDTO.getGender() != null ? registerDTO.getGender() : 0);
        user.setBirthday(registerDTO.getBirthday());
        user.setRegionCode(registerDTO.getRegionCode());
        user.setSellerCredit(-1);
        user.setBuyerCredit(-1);
        user.setCreateTime(new Date());

        /* TODO 上传图片 */
        user.setAvatarImageId(0);

        // 保存用户
        this.save(user);

        // 构建返回结果
        UserRegisterVO registerVO = new UserRegisterVO();
        registerVO.setUserId(user.getUserId());
        registerVO.setUserName(user.getUserName());
        registerVO.setAccount(user.getAccount());
        registerVO.setPhone(user.getPhone());
        registerVO.setCreateTime(user.getCreateTime().toString());

        return registerVO;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", loginDTO.getAccount());
        User user = this.getOne(queryWrapper);

        if (user == null) {
            throw new RuntimeException("账户不存在");
        }

        // 验证密码（注意：此为明文密码比较，实际项目中应使用加密方式）
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("密码错误");
        }


        // 生成并返回token（此处简化处理，实际应生成JWT或session token）

        return jwtUtil.generateToken(user.getUserId(),user.getUserName(), 7 * 24 * 60 * 60 * 1000);
    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        // 从上下文获取当前用户ID
        String userId = UserContext.getCurrentUserId();

        // 添加空值检查
        if (userId == null || userId.isEmpty()) {
            System.out.println("用户未登录");
            throw new RuntimeException("用户未登录");
        }

        // 构建更新条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);

        // 构建更新对象
        User user = new User();
        if (userUpdateDTO.getUserName() != null) {
            user.setUserName(userUpdateDTO.getUserName());
        }
        if (userUpdateDTO.getGender() != null) {
            user.setGender(userUpdateDTO.getGender());
        }
        if (userUpdateDTO.getBirthday() != null) {
            user.setBirthday(userUpdateDTO.getBirthday());
        }
        if (userUpdateDTO.getRegionCode() != null) {
            user.setRegionCode(userUpdateDTO.getRegionCode());
        }
        if (userUpdateDTO.getAvatarImageId() != null) {
            user.setAvatarImageId(userUpdateDTO.getAvatarImageId());
        }

        // 执行更新
        this.update(user, queryWrapper);
    }


}

