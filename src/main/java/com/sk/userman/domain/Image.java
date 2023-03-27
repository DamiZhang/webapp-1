package com.sk.userman.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("image")
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @TableField("image_id")
    private Long imageId;

    @TableField("product_id")
    private Long productId;

    @TableField("fileName")
    private String fileName;

    @TableField("date_created")
    private Date dateCreated;

    @TableField("s3_bucket_path")
    private String s3BucketPath;

}
