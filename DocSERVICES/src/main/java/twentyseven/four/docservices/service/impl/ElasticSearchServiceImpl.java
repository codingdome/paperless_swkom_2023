package twentyseven.four.docservices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docservices.elasticsearch.ElasticFileEntity;
import twentyseven.four.docservices.elasticsearch.ElasticSearchRepository;
import twentyseven.four.docservices.service.ElasticSearchService;

import java.io.File;
import java.util.List;

@Service
@Transactional
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    ElasticSearchRepository elasticSearchRepository;

    @Override
    public void save(File file, String content) {
        ElasticFileEntity elasticFileEntity = ElasticFileEntity.builder()
                .name(file.getName())
                .content(content)
                .build();

        elasticSearchRepository.save(elasticFileEntity);
    }

    @Override
    public Iterable<ElasticFileEntity> findAll() {
        return elasticSearchRepository.findAll();
    }
}
