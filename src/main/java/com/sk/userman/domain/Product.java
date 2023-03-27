package com.sk.userman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName product
 */
@Data
@TableName("product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String name;
    private String description;
    private String sku;
    private String manufacturer;
    /**
     *  0-100
     */
    private Integer quantity;
    private Date dateAdded;
    private Date dateLastUpdated;
    private Long ownerUserId;


}