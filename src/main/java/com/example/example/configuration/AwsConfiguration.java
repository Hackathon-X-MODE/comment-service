package com.example.example.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.example.configuration.property.AwsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfiguration {

    @Bean
    public AmazonS3 amazonS3(AwsProperties awsProperties) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                awsProperties.getCred().getAccessToken(),
                                awsProperties.getCred().getSecretKey()
                        )
                ))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                awsProperties.getCred().getHost(),
                                awsProperties.getCred().getRegion()
                        )
                )
                .build();
    }

    @Bean
    public AwsProperties.AwsBucket awsBucket(AwsProperties awsProperties) {
        return awsProperties.getBucket();
    }

}
