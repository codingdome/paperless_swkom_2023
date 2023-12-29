package com.example.docprotocol.services;

import com.example.docprotocol.entity.FileEntity;
import com.example.docprotocol.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SqlDBService {

    @Autowired
    private FileRepository fileRepository;

    // Function to return the count of current entries in the database
    public long getCountOfFiles() {
        return fileRepository.count();
    }

    // Function to return a list of FileEntity with all entities currently in the database
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    // Function to delete all entities in the database
    public void deleteAllFiles() {
        fileRepository.deleteAll();
    }

}
