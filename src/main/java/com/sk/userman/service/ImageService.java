package com.sk.userman.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sk.userman.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService  extends IService<Image> {

    List<Image> findAllByProductId(Long productId);

    Image uploadImage(Long productId, MultipartFile image);

    void deleteImage(Long imageId);
}
