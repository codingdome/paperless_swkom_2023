package com.example.docprotocol.controller;

import com.example.docprotocol.services.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/elasticsearch")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    // Endpoint to get the count of current ElasticFileEntitys
    @GetMapping("/file-count")
    public ResponseEntity<Long> getFileCount() {
        long count = elasticSearchService.getCountOfElasticFileEntities();
        return ResponseEntity.ok(count);
    }

    // Endpoint to get a list of all file names in Elasticsearch
    @GetMapping("/file-names")
    public ResponseEntity<List<String>> getAllFileNames() {
        List<String> fileNames = elasticSearchService.getAllFileNames();
        return ResponseEntity.ok(fileNames);
    }

    // Endpoint to delete all entries in Elasticsearch
    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllFiles() {
        elasticSearchService.deleteAllElasticFileEntities();
        return ResponseEntity.ok().build();
    }

}
