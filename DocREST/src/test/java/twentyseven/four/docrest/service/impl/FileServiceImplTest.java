package twentyseven.four.docrest.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import twentyseven.four.docrest.model.File;
import twentyseven.four.docrest.service.FileService;

import java.util.Date;
import java.util.List;

@SpringBootTest
class FileServiceImplTest {

    @Autowired
    FileService fileService;

    @Test
    void saveNewFileToPostgreSQL() {
        File file = File.builder()
                .name("test")
                .ocrExtractedText("ocr text")
                .size(1024)
                .uploadTime(new Date())
                .changedTime(new Date())
                .contentType("application/pdf")
                .build();

        fileService.save(file);
    }

    @Test
    void getAllFilesFromPostgreSQL() {
        File file = File.builder()
                .name("test")
                .ocrExtractedText("ocr text")
                .size(1024)
                .uploadTime(new Date())
                .changedTime(new Date())
                .contentType("application/pdf")
                .build();
        fileService.save(file);
        List<File> files = fileService.getAll();
        for(File foundFile : files) {
            System.out.println(foundFile.getName());
        }
    }

    @Test
    void testFindByName() {
        File found = fileService.findByName("Archive.pdf");
        System.out.println(found.getName());
    }

}