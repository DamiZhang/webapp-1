package com.sk.userman.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@Data
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    @Email
    private String username;
    private Date accountCreated;
    private Date accountUpdate;

}