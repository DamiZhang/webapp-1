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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author: zsc
 * @create: 2023-02-10 09:56
 **/
@RequestMapping("/v1/product")
@RestController
@Api(tags = "产品接口管理")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("{productId}")
    @ApiOperation("查询产品")
    public Result query(@PathVariable Integer productId){
        Product data = productService.getById(productId);
        if (data == null){
            throw new BusinessException("产品不存在");
        }
        Result<Object> result = new Result<>();
        result.ok(data);
        result.setCode(204);
        return result;
    }


    @PostMapping
    @ApiOperation("添加产品")
    public Result save(@RequestBody @Validated ProductDTO dto){
        Product product = new Product();
        Result<Object> result = new Result<>();

        result.setCode(204);
        Product one = productService.getOne(new LambdaQueryWrapper<Product>().eq(Product::getSku, dto.getSku()));

        if (one != null){
            result.setCode(400);
            result.setMsg("sku已存在");
            return result;
        }

        BeanUtils.copyProperties(dto,product);
        product.setOwnerUserId(BaseContext.get());
        product.setDateAdded(new Date());
        product.setDateLastUpdated(new Date());
        productService.save(product);

        return result;
    }


    @PutMapping("{productId}")
    @ApiOperation("修改产品Update Product")
    @ApiImplicitParam(value = "产品接收实体类Product Get Entity")
    public Result update(@RequestBody @Validated ProductDTO dto,@PathVariable Integer productId){
        dto.setId(productId);
        Product product = productService.getById(dto.getId());
        String userId = BaseContext.get();

        Result<Object> result = new Result<>();
        result.setCode(204);
        if (product == null){
            result.setCode(404);
            result.setMsg("产品不存在Product not exist");

            return result;
        }
        if (!userId.equals(product.getOwnerUserId())){
            result.setCode(403);
            result.setMsg("产品不是你创建的Product not created by you");
            return result;
        }
        BeanUtils.copyProperties(dto,product);
        product.setDateLastUpdated(new Date());
        productService.updateById(product);
        return result;
    }



    @DeleteMapping("{productId}")
    @ApiOperation("删除产品Delete Product")
    public Result delete(@PathVariable Integer productId){
        Product product = productService.getById(productId);
        String userId = BaseContext.get();
        Result<Object> result = new Result<>();
        result.setCode(204);

        if (product == null){
            result.setCode(404);
            result.setMsg("产品不存在Product not exist");

            return result;
        }
        if (!userId.equals(product.getOwnerUserId())){
            result.setCode(403);
            result.setMsg("产品不是你创建的Product not created by you");

            return result;
        }
        boolean b = productService.removeById(productId);

        if (b){
            result.setMsg("删除成功Delete Successfully");
            return result;
        }
        result.setMsg("产品已被删除，请勿重新操作Deleted, no repeated action needed");
        return result;
    }
}
