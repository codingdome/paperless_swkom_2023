package twentyseven.four.docrest.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docrest.model.File;
import twentyseven.four.docrest.persistence.entity.FileEntity;
import twentyseven.four.docrest.persistence.repository.FileRepository;
import twentyseven.four.docrest.service.FileService;
import twentyseven.four.docrest.service.mapper.FileMapper;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    @Override
    public File findByName(String name) {
        return fileRepository.findByName(name)
                .map(fileMapper::fromEntity)
                .orElse(new File());
    }

    @Override
    public File save(File file) {
        logger.info("--- SAVING --- " + file.getName());

        // Check if a file with the same name already exists
        Optional<FileEntity> existingFileOpt = fileRepository.findByName(file.getName());

        FileEntity fileEntity = fileMapper.toEntity(file); // Convert DTO to entity

        existingFileOpt.ifPresent(existingFile -> {
            // If a file with the same name exists, set its ID to the new entity
            // This ensures the existing entity is updated rather than creating a new one
            fileEntity.setId(existingFile.getId());
        });

        // Save the entity (new or updated)
        File savedFile = fileMapper.fromEntity(fileRepository.save(fileEntity));

        logger.info("--- SAVED --- " + savedFile.getName());
        return savedFile;
    }


    @Override
    public List<File> getAll() {

        // LOGGING
        logger.info("--- GETTING ALL ---");

        return fileMapper.fromEntity(fileRepository.findAll());
    }

    @Override
    public void delete(String name) {
        // LOGGING
        logger.info("--- DELETING --- " + name);

        Optional<FileEntity> fileEntityOptional = fileRepository.findByName(name);
        if (fileEntityOptional.isPresent()) {
            fileRepository.delete(fileEntityOptional.get());
            // LOGGING
            logger.info("--- DELETED --- " + name);

            // DEBUGGING
            for (File file: getAll()
            ) {
                logger.info("--- CURRENTLY IN DB: " + file.getName());
            }
            // END DEBUGGING

        } else {
            throw new RuntimeException("File not found: " + name);
        }
    }




}