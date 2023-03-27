package com.sk.userman.controller;

import com.sk.userman.domain.User;
import com.sk.userman.dto.LoginDTO;
import com.sk.userman.dto.UserDTO;
import com.sk.userman.service.UserService;
import com.sk.userman.utils.Result;
import com.sk.userman.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**

 **/
@RequestMapping("/v1/user")
@RestController
@Api(tags="User")
public class UserController {

    /**
     *
     * controller  -> service -> dao/mapper
     *
     */

    @Autowired
    private UserService userService;

    @ApiOperation("登录Login")
    @PostMapping("login")
    public Result<String> login(@Validated @RequestBody LoginDTO loginDTO){
        String token = userService.login(loginDTO);
        Result<String> stringResult = new Result<>();
        stringResult.setResult(token);
        return stringResult;
    }

    @ApiOperation("注册Register")
    @PostMapping
    public Result register( @Validated @RequestBody User user) throws Exception {
        userService.register(user);
        return new Result<>().ok("注册成功Register Successfully");
    }

    @ApiOperation(value = "获取当前用户信息Access the current user information",response = UserVO.class)
    @GetMapping("{userId}")
    public ResponseEntity<String> info(@PathVariable Integer userId){

        UserVO info = userService.info(userId +"");
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @ApiOperation("修改当前用户信息Update user information")
    @PutMapping("{userId}")
    public Result updateInfo(@PathVariable Integer userId,@Validated @RequestBody UserDTO userDTO) throws Exception {
        userDTO.setId(userId);
        userService.updateInfo(userDTO);
        return new Result<>().ok("更新成功Updated successfully");
    }

}
