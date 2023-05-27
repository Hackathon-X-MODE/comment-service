package com.example.example.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.example.example.configuration.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final AmazonS3 amazonS3;

    private final AwsProperties.AwsBucket bucket;


    public String upload(String path, InputStream inputStream) throws InterruptedException {
        final var tm = TransferManagerBuilder.standard().withS3Client(this.amazonS3).build();
        Upload upload = tm.upload(this.bucket.getName(), path, inputStream, new ObjectMetadata());
        upload.waitForCompletion();
        return this.bucket.getPublicUrl() + path;
    }
}
