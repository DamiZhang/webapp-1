package com.sk.userman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sk.userman.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @Entity com.sk.userman.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username}")
    User getByUsername(@Param("username")String username);

}
