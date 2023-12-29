package com.example.docprotocol.controller;

import com.example.docprotocol.services.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioService minioService;

    @DeleteMapping("/delete-all-files")
    public ResponseEntity<Void> deleteAllObjects() {
        minioService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/object-count")
    public ResponseEntity<Integer> getObjectCount() {
        int count = minioService.getObjectCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/object-names")
    public ResponseEntity<List<String>> getObjectNames() {
        List<String> filenames = minioService.getAllFilenames();
        return ResponseEntity.ok(filenames);
    }

}
