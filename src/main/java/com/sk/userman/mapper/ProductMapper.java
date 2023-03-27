package com.sk.userman.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sk.userman.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @Entity com.sk.userman.domain.Product
*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {


}
