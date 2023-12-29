package twentyseven.four.docservices.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import twentyseven.four.docservices.elasticsearch.ElasticSearchRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElasticSearchServiceImplTest {

    @Autowired
    ElasticSearchRepository elasticSearchRepository;

    @Test
    void deleteAll() {
        elasticSearchRepository.deleteAll();
    }
}