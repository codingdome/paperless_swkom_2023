package twentyseven.four.docrest.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docrest.controller.SearchController;
import twentyseven.four.docrest.model.File;
import twentyseven.four.docrest.persistence.entity.ElasticFileEntity;
import twentyseven.four.docrest.persistence.repository.ElasticSearchRepository;
import twentyseven.four.docrest.service.FileService;
import twentyseven.four.docrest.service.SearchService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    private static final Logger logger = Logger.getLogger(SearchServiceImpl.class);

    @Autowired
    ElasticSearchRepository elasticSearchRepository;

    @Autowired
    FileService fileService;

    public List<File> findByContent(String content) {

        List<File> results = new ArrayList<>();

        List<ElasticFileEntity> elasticFileEntities = elasticSearchRepository.searchByContent(content);

        for (ElasticFileEntity elasticFileEntity: elasticFileEntities
        ) {
            System.out.println(elasticFileEntity.getName());
            results.add(fileService.findByName(elasticFileEntity.getName()));
        }
        return results;
    }

    public List<File> findByName(String name) {

        List<File> results = new ArrayList<>();

        List<ElasticFileEntity> elasticFileEntities = elasticSearchRepository.searchByName(name);

        for (ElasticFileEntity elasticFileEntity: elasticFileEntities
        ) {
            results.add(fileService.findByName(elasticFileEntity.getName()));
        }
        return results;
    }

    @Override
    public List<File> search(String query) {
        logger.info("--- SEARCHING FOR ---" + query);
        List<File> filesFoundByContent = findByContent(query);
        for (File file: filesFoundByContent
        ) {
            logger.info("--- FOUND ---" + file.getName());
        }
        List<File> filesFoundByName = findByName(query);
        for (File file: filesFoundByName
        ) {
            if (!filesFoundByContent.contains(file)) {
                filesFoundByContent.add(file);
            }
        }
        return filesFoundByContent;
    }

    @Override
    public void deleteByName(String filename) {
        elasticSearchRepository.deleteById(filename);
    }
}
