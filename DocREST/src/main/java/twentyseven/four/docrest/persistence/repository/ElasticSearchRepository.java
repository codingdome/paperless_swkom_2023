package twentyseven.four.docrest.persistence.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import twentyseven.four.docrest.persistence.entity.ElasticFileEntity;

import java.util.List;

public interface ElasticSearchRepository extends ElasticsearchRepository<ElasticFileEntity, String> {

    // Custom query using Query DSL for full text search on the 'content' field
    @Query("{\"match\": {\"content\": \"?0\"}}")
    List<ElasticFileEntity> searchByContent(String content);

    // Custom query using Query DSL for full text search on the 'name' field
    @Query("{\"match\": {\"name\": \"?0\"}}")
    List<ElasticFileEntity> searchByName(String name);

}
