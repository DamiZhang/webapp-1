package com.sk.userman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sk.userman.domain.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;


@Mapper
public interface ImageMapper extends BaseMapper<Image> {

    @Select("select * from image where product_id = #{productId}")
    List<Image> findAllByProductId(@Param("productId")Long productId);
}
