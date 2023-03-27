package com.sk.userman.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sk.userman.domain.Product;
import com.sk.userman.dto.ProductDTO;
import com.sk.userman.exception.BusinessException;
import com.sk.userman.service.ProductService;
import com.sk.userman.utils.BaseContext;
import com.sk.userman.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author: zsc
 * @create: 2023-02-10 09:56
 **/
@RequestMapping("/v1/product")
@RestController
@Api(tags = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("{productId}")
    @ApiOperation("查询产品")
    public ResponseEntity<String> query(@PathVariable Integer productId){
        Product data = productService.getById(productId);
        if (data == null){
            throw new BusinessException("产品不存在");
        }
        return new ResponseEntity(data, HttpStatus.CREATED);
    }


    @PostMapping
    @ApiOperation("添加产品")
    public ResponseEntity<String> save(@RequestBody @Validated ProductDTO dto){
        Product product = new Product();

        Product one = productService.getOne(new LambdaQueryWrapper<Product>().eq(Product::getSku, dto.getSku()));

        if (one != null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        BeanUtils.copyProperties(dto,product);
//        product.setOwnerUserId(BaseContext.get());
        product.setDateAdded(new Date());
        product.setDateLastUpdated(new Date());
        productService.save(product);

        return new ResponseEntity(product, HttpStatus.CREATED);
    }


    @PutMapping("{productId}")
    @ApiOperation("修改产品Update Product")
    @ApiImplicitParam(value = "产品接收实体类Product Get Entity")
    public ResponseEntity<String> update(@RequestBody @Validated ProductDTO dto,@PathVariable Integer productId){
        dto.setId(productId);
        Product product = productService.getById(dto.getId());
        String userId = BaseContext.get();

        if (product == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        if (!userId.equals(product.getOwnerUserId())){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        BeanUtils.copyProperties(dto,product);
        product.setDateLastUpdated(new Date());
        productService.updateById(product);
        return new ResponseEntity(product, HttpStatus.OK);
    }



    @DeleteMapping("{productId}")
    @ApiOperation("删除产品Delete Product")
    public ResponseEntity<String> delete(@PathVariable Integer productId){
        Product product = productService.getById(productId);
        String userId = BaseContext.get();

        if (product == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        if (!userId.equals(product.getOwnerUserId())){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        boolean b = productService.removeById(productId);

        if (b){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
