package twentyseven.four.docservices.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docservices.model.File;
import twentyseven.four.docservices.persistence.entity.FileEntity;
import twentyseven.four.docservices.persistence.repository.FileRepository;
import twentyseven.four.docservices.service.FileService;
import twentyseven.four.docservices.service.mapper.FileMapper;


@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileMapper fileMapper;

    private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

    @Override
    public void update(String name, String ocrResult) {
        FileEntity foundFile = fileRepository.findByName(name);
        System.out.println("--FOUND FILE:--" + foundFile.getName());
        System.out.println("--FOUND FILE:--" + foundFile.getId());
        if(foundFile == null) {
            return;
        }

        logger.info("--- FOUND FILE ---" + foundFile.getName());

        foundFile.setOcrExtractedText(ocrResult);
        fileRepository.save(foundFile);
    }
}
