package com.sk.userman.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sk.userman.common.DateUtils;
import com.sk.userman.common.s3.S3Helper;
import com.sk.userman.domain.Image;
import com.sk.userman.domain.User;
import com.sk.userman.mapper.ImageMapper;
import com.sk.userman.mapper.ProductMapper;
import com.sk.userman.mapper.UserMapper;
import com.sk.userman.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private S3Helper s3Helper;

    @Override
    public List<Image> findAllByProductId(Long productId) {
        return imageMapper.findAllByProductId(productId);
    }

    @Override
    public Image uploadImage(Long productId, MultipartFile image) {

        Image imageTemplate = new Image();
        imageTemplate.setProductId(productId);
        imageMapper.insert(imageTemplate);

        String bucketName = s3Helper.getBucketName();
        String keyName = imageTemplate.getImageId() + "_" + image.getOriginalFilename();
        s3Helper.upload(keyName, image);

        imageTemplate.setFileName(keyName);

        imageTemplate.setS3BucketPath("https://" + bucketName + ".s3.amazonaws.com/" + keyName);
        imageTemplate.setDateCreated(DateUtils.getNowDate());
        imageMapper.updateById(imageTemplate);

        return imageTemplate;
    }

    @Override
    public void deleteImage(Long imageId) {
        Image image = imageMapper.selectById(imageId);
        s3Helper.deleteObject(image.getFileName());
        imageMapper.deleteById(imageId);
    }
}
