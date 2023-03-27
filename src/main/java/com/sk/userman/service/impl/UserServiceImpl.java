package com.sk.userman.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sk.userman.domain.User;
import com.sk.userman.dto.LoginDTO;
import com.sk.userman.dto.UserDTO;
import com.sk.userman.exception.BusinessException;
import com.sk.userman.mapper.UserMapper;
import com.sk.userman.service.UserService;
import com.sk.userman.utils.BCryptUtils;
import com.sk.userman.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;




    @Override
    public void register(User user) throws Exception {

        User oneUser = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (oneUser == null) {
            //进行加密
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setAccountCreated(new Date());
            user.setAccountUpdate(new Date());

            save(user);


        } else {
            throw new BusinessException("邮箱已被注册");
        }
    }

    @Override
    public String login(LoginDTO loginDTO) {

        User user = getOne(new QueryWrapper<User>().eq("username", loginDTO.getUserName()));


        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (BCryptUtils.matches(loginDTO.getPassword(), user.getPassword())) {
            return "ok";
        }
        throw new BusinessException("用户名或密码错误");
    }

    @Override
    public UserVO info(String userId) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("userId not exist");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;

    }

    @Override
    public void updateInfo(UserDTO userDTO) throws Exception {

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setAccountUpdate(new Date());
        updateById(user);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }
}
