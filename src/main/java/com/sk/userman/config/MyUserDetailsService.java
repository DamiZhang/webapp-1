package com.sk.userman.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sk.userman.domain.User;
import com.sk.userman.mapper.UserMapper;
import com.sk.userman.utils.BaseContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author tlh
 * @date 2022/11/17 23:52
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        com.sk.userman.config.User user1 = new com.sk.userman.config.User();
        user1.setUsername(username);
        BaseContext.set(username);
        BeanUtils.copyProperties(user,user1);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在User does not exist");
        }
        user1.setRoles(new ArrayList<>());
        return user1;
    }
}