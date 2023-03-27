package com.sk.userman.common.s3;


import com.sk.userman.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class S3Helper {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;


    public String getBucketName() {
        return bucketName;
    }

    public PutObjectResponse upload(String keyName, MultipartFile file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        PutObjectResponse response = null;

        try {
            response = s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        }catch (IOException ioException){
            log.error("S3Helper | Failed to put Ojbect", ioException);
            throw new BusinessException("Upload File Exception");
        }

        return response;
    }

    public PutObjectResponse upload(String keyName, File file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        PutObjectResponse response = null;

        response = s3Client.putObject(request, RequestBody.fromFile(file));

        return response;
    }


    public ResponseInputStream<GetObjectResponse> download(String keyName) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        return s3Client.getObject(request);
    }


    public DeleteObjectResponse deleteObject(String keyName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        return s3Client.deleteObject(request);
    }

    public List<String> listObjectsAndFolders(String prefix) {
        List<String> fileList = new ArrayList<>();
        String marker = null;
        ListObjectsResponse response = null;

        do {
            ListObjectsRequest request = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .delimiter("/")
                    .maxKeys(1000)
                    .marker(marker)
                    .prefix(prefix)
                    .build();

            response = s3Client.listObjects(request);
            marker = response.nextMarker();

            for (CommonPrefix commonPrefix : response.commonPrefixes()) {
                fileList.add(commonPrefix.prefix().substring(prefix.length()));
            }

            List<S3Object> s3ObjectList = response.contents();

            if (s3ObjectList.size() == 1 && s3ObjectList.get(0).key().equals(prefix)) {
                continue;
            }
            for (S3Object s3Object : s3ObjectList) {
                fileList.add(s3Object.key().substring(prefix.length()));
            }

        } while (response.isTruncated());

        return fileList;
    }


    public GetObjectResponse getObject(String keyName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        return s3Client.getObject(getObjectRequest).response();
    }

}
