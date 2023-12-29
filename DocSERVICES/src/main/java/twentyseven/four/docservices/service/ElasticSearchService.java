package twentyseven.four.docservices.service;

import twentyseven.four.docservices.elasticsearch.ElasticFileEntity;

import java.io.File;

public interface ElasticSearchService {

    void save(File file, String content);

    Iterable<ElasticFileEntity> findAll();
}
