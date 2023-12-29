package com.example.docprotocol.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MinioServiceTest {

    @Autowired
    MinioService minioService;

    @Test
    void deleteAll() {
        minioService.deleteAll();
    }

    @Test
    void getObjectCount() {
        System.out.println( minioService.getObjectCount());
    }

    @Test
    void getListOfAllFilenames() {
        minioService.getAllFilenames();
    }

}