//package com.epam.resourceservice.configuration.storage;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class S3Configuration {
//
//    @Value("${cloud.aws.credentials.accessKey}")
//    String accessKey;
//    @Value("${cloud.aws.credentials.secretKey}")
//    String accessSecret;
//    @Value("${cloud.aws.region}")
//    private String region;
//
//    @Bean
//    public AmazonS3 amazonS3Client() {
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey,accessSecret);
//
//        AmazonS3 s3client = AmazonS3ClientBuilder
//                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(Regions.DEFAULT_REGION)
////                .withRegion(Regions.fromName(region))
//                .build();
//        return s3client;
//    }
//
//}
