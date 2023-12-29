package com.example.docprotocol.controller;

import com.example.docprotocol.entity.FileEntity;
import com.example.docprotocol.services.SqlDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sql")
public class SqlDatabaseController {

    @Autowired
    private SqlDBService sqlDBService;

    // Endpoint to get the count of current entries in the database
    @GetMapping("/file-count")
    public ResponseEntity<Long> getFileCount() {
        long count = sqlDBService.getCountOfFiles();
        return ResponseEntity.ok(count);
    }

    // Endpoint to get a list of FileEntity with all entities currently in the database
    @GetMapping("/files")
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        List<FileEntity> files = sqlDBService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    // Endpoint to delete all entities in the database
    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllFiles() {
        sqlDBService.deleteAllFiles();
        return ResponseEntity.ok().build();
    }

}
