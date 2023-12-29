package twentyseven.four.docservices.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docservices.elasticsearch.ElasticFileEntity;
import twentyseven.four.docservices.service.*;
import twentyseven.four.docservices.util.FileExtension;

import java.io.File;

@Service
@Transactional
public class DocumentProcessingServiceImpl implements DocumentProcessingService {

    private static final Logger logger = Logger.getLogger(DocumentProcessingServiceImpl.class);

    @Autowired
    FetchFileService fetchFileService;

    @Autowired
    OCRService ocrService;

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired
    FileService fileService;

    @Override
    public void start(String fileName) throws Exception {

        //1: Fetch from MinIO
        logger.info("--- FETCHING MINIO ---");
        File fetchResult = fetchFileService.getFile(fileName);
        logger.info("--- RECEIVED --- " + fetchResult.getName());

        //2: Perform OCR
        String fileExtension = FileExtension.getFileExtension(fetchResult);

        String ocrResultString;
        if("pdf".equalsIgnoreCase(fileExtension)) {
            logger.info("--- PROCESSING PDF OCR ---");
            ocrResultString = ocrService.processPdfOCR(fetchResult);
        } else if ("png".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension)) {
            logger.info("--- PROCESSING IMAGE OCR ---");
            ocrResultString = ocrService.processImageOCR(fetchResult);
        } else {
            logger.info("--- UNSUPPORTED FILE TYPE ---");
            throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }

//        logger.info("--- PERFORMING OCR ---");
//        String ocrResultString = ocrService.processImageOCR(fetchResult);
//        logger.info("--- RESULT ---" + ocrResultString);

        //3: Save Results to postgreSQL
        logger.info("--- UPDATE SQL DB ---");
        fileService.update(fileName, ocrResultString);

        //4: Save Results to ElasticSearch
        logger.info("--- SAVING TO ELASTICSEARCH ---");
        elasticSearchService.save(fetchResult, ocrResultString);

        //DEBUG
//        logger.info("--- CURRENTLY IN ELASTIC SEARCH ---");
//        for (ElasticFileEntity result : elasticSearchService.findAll()
//        ) {
//            logger.info(result.getName().toUpperCase() + " --- " + result.getContent());
//        }
        //END DEBUGGING

    }
}
