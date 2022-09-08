package com.gk.portfolio.config;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
@Slf4j
public class S3CloudConfig {


//    @Value("${accessKey}")
    String accessKey;
//    @Value("${secretKey}")
    String secretKey;
    @Value("${region}")
    String region;
    @Value("${bucketName}")
    String bucketName;


    @Bean
    public AmazonS3 getS3client() {
        DefaultAWSCredentialsProviderChain props = new DefaultAWSCredentialsProviderChain();

        AWSCredentials credentials = props.getCredentials();
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public Bucket createBucketIfNeed(){
        Bucket bucket = null;
        if(getS3client().doesBucketExistV2(bucketName)){
            bucket = getS3client().listBuckets().stream()
                    .filter(bucket1 -> bucket1.getName().equals(bucketName))
                    .collect(Collectors.toList()).get(0);
            return bucket;
        } else {
            try {
                bucket = getS3client().createBucket(bucketName);
            }catch (AmazonS3Exception ex){
                log.error(ex.getMessage());
            }
        }
        return bucket;

    }


}
