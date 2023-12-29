package twentyseven.four.docrest.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import twentyseven.four.docrest.persistence.entity.ElasticFileEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElasticSearchRepositoryTest {

    @Autowired
    ElasticSearchRepository elasticSearchRepository;

    @Test
    void testFindByContent() {
        for (ElasticFileEntity file :elasticSearchRepository.searchByContent("Wikibooks: Offene")
        ) {
            System.out.println(file.getName());
        }
    }

    @Test
    void textFindByName() {
        for (ElasticFileEntity file : elasticSearchRepository.searchByName("Projekt")
        ){
            System.out.println(file.getName());
        }
    }
}