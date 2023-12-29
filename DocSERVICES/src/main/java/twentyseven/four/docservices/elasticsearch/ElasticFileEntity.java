package twentyseven.four.docservices.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@Builder
@Document(indexName = "files")
public class ElasticFileEntity {

    @Id
    private String name;
    private String content;
}
