package com.example.docprotocol.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SqlDBServiceTest {

    @Autowired
    SqlDBService sqlDBService;

    @Test
    void testDeleteAll() {
        sqlDBService.deleteAllFiles();
    }

    @Test
    void testGetAll() {
        sqlDBService.getAllFiles();
    }

    @Test
    void testGetCount() {
        sqlDBService.getCountOfFiles();
    }

}