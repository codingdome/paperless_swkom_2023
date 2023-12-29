package com.example.docprotocol.repository;

import com.example.docprotocol.entity.ElasticFileEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchRepository extends ElasticsearchRepository<ElasticFileEntity, String> {
}
