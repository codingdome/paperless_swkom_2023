package com.example.docprotocol.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Test
    void testGetAll() {
        fileRepository.findAll();
    }

    @Test
    void testCountAll() {
        System.out.println(fileRepository.count());
    }

    @Test
    void deleteAll() {
        fileRepository.deleteAll();
    }
}