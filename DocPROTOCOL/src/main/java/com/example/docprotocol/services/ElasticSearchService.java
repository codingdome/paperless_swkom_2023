package com.example.docprotocol.services;

import com.example.docprotocol.entity.ElasticFileEntity;
import com.example.docprotocol.repository.ElasticSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ElasticSearchService {

    @Autowired
    private ElasticSearchRepository elasticSearchRepository;

    // Function to get the count of current ElasticFileEntitys
    public long getCountOfElasticFileEntities() {
        return elasticSearchRepository.count();
    }

    // Function to get a list of all file names in Elasticsearch
    public List<String> getAllFileNames() {
        Iterable<ElasticFileEntity> entities = elasticSearchRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), false)
                .map(ElasticFileEntity::getName)
                .collect(Collectors.toList());
    }

    // Function to delete all entries in Elasticsearch
    public void deleteAllElasticFileEntities() {
        elasticSearchRepository.deleteAll();
    }

}
