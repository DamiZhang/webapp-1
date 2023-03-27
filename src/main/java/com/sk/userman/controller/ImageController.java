package com.sk.userman.controller;

import com.sk.userman.domain.Image;
import com.sk.userman.domain.Product;
import com.sk.userman.service.ImageService;
import com.sk.userman.service.ProductService;
import com.sk.userman.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.util.List;

@RequestMapping("/v1/product/{product_id}/image")
@RestController
@Api(tags = "Image")
public class ImageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;

    @GetMapping()
    public ResponseEntity<String> getAllImage(@PathVariable("product_id") Long productId) {
        Product product = productService.getById(productId);
        if(product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<Image> images = imageService.findAllByProductId(productId);

        return new ResponseEntity(images, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@PathVariable("product_id") Long productId,
                                              @RequestParam("image") MultipartFile image) {
        Product product = productService.getById(productId);
        if(product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (image.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        // Check if file is an image
        if (!image.getContentType().startsWith("image/")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Image imageInfo = imageService.uploadImage(productId, image);

        return new ResponseEntity(imageInfo, HttpStatus.CREATED);
    }

    @GetMapping("/{image_id}")
    public ResponseEntity<String> getImage(@PathVariable("product_id") Long productId,
                                           @PathVariable("image_id") Long imageId) {
        Product product = productService.getById(productId);
        if(product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Image image = imageService.getById(imageId);
        if (image == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(!image.getProductId().equals(productId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(image, HttpStatus.OK);
    }

    @DeleteMapping("/{image_id}")
    public ResponseEntity<String> deleteImage(@PathVariable("product_id") Long productId,
                                              @PathVariable("image_id") Long imageId) {
        Product product = productService.getById(productId);
        if(product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Image image = imageService.getById(imageId);
        if (image == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(!image.getProductId().equals(productId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        imageService.deleteImage(imageId);

        return new ResponseEntity(HttpStatus.OK);
    }


}
