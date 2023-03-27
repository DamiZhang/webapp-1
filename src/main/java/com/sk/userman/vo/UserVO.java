package com.sk.userman.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author: zsc
 * @create: 2023-01-29 22:22
 **/
@Data
@ApiModel("返回用户信息实体")
public class UserVO {

    @ApiModelProperty("用户id")
    private Integer id;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")

    private String username;

    /**
     * 姓
     */
    @ApiModelProperty("姓")
    private String firstName;

    /**
     * 名
     */
    @ApiModelProperty("名")
    private String lastName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date accountCreated;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date accountUpdate;
}
