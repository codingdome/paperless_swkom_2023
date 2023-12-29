package twentyseven.four.docservices.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import twentyseven.four.docservices.model.File;
import twentyseven.four.docservices.persistence.entity.FileEntity;
import twentyseven.four.docservices.persistence.repository.FileRepository;
import twentyseven.four.docservices.service.FileService;
import twentyseven.four.docservices.service.mapper.FileMapper;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceImplTest {

    @Autowired
    FileService fileService;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileMapper fileMapper;

    @Test
    @Rollback
    void saveAFewFilesAndFindOneAndUpdateIt() {
        File file = File.builder()
                .name("TestName")
                .changedTime(new Date())
                .uploadTime(new Date())
                .ocrExtractedText("ocr loading...")
                .size(123456)
                .build();
        File fileTwo = File.builder()
                .name("TestNameAnother")
                .changedTime(new Date())
                .uploadTime(new Date())
                .ocrExtractedText("ocr loading...")
                .size(7891011)
                .build();

        fileRepository.save(fileMapper.toEntity(file));
        fileRepository.save(fileMapper.toEntity(fileTwo));

        String filename = "TestName";
        String ocrText = "This is the new OCR Text";

        FileEntity result = fileRepository.findByName("TestName");
        assertEquals(123456, result.getSize());
        assertEquals("ocr loading...", result.getOcrExtractedText());

        fileService.update(filename, ocrText);

        assertEquals("This is the new OCR Text", fileRepository.findByName(filename).getOcrExtractedText());
    }

}