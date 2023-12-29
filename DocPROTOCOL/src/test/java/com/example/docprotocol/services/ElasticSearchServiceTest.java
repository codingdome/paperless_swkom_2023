package com.example.docprotocol.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElasticSearchServiceTest {

    @Autowired
    ElasticSearchService elasticSearchService;

    @Test
    void getCount() {
        System.out.println(elasticSearchService.getCountOfElasticFileEntities());
    }

    @Test
    void getNames() {
        List<String> results = elasticSearchService.getAllFileNames();
        for (String name : results) {
            System.out.println(name);
        }
    }

    @Test
    void deleteAll() {
        elasticSearchService.deleteAllElasticFileEntities();
        assertEquals(0, elasticSearchService.getCountOfElasticFileEntities());
    }
}