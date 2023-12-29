package twentyseven.four.docservices.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import twentyseven.four.docservices.service.OCRService;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OCRServiceImplTest {

    @Autowired
    OCRService ocrService;

    @Test
    void testOCRWithTesseract() {
        File file = new File("/Users/dominikenglert/JAVA/project_2023/test_files/img/testImg2.png");

        System.out.println(ocrService.processImageOCR(file));
    }

    @Test
    void testOCRWithPDFBox() throws IOException {
        File file = new File("/Users/dominikenglert/JAVA/project_2023/test_files/pdf/CI-CD-SWQM.pdf");

        System.out.println(ocrService.processPdfOCR(file));
    }

}