package com.example.docprotocol.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfiguration {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.rootUser}")
    private String minioRootUser;

    @Value("${minio.rootPassword}")
    private String minioRootPassword;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioRootUser, minioRootPassword)
                .build();
    }
}