package twentyseven.four.docrest.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileTest {
    @Test
    void testObjectCreationAndFieldAccess() {
        Date now = new Date();
        File file = new File("test.txt", "text/plain", 1024, "Sample text", now, now);

        assertEquals("test.txt", file.getName());
        assertEquals("text/plain", file.getContentType());
        assertEquals(1024, file.getSize());
        assertEquals("Sample text", file.getOcrExtractedText());
        assertEquals(now, file.getUploadTime());
        assertEquals(now, file.getChangedTime());
    }

    @Test
    void testBuilderPatternFunctionality() {
        Date now = new Date();
        File file = File.builder()
                .name("test.txt")
                .contentType("text/plain")
                .size(1024)
                .ocrExtractedText("Sample text")
                .uploadTime(now)
                .changedTime(now)
                .build();

        assertEquals("test.txt", file.getName());
        assertEquals("text/plain", file.getContentType());
        assertEquals(1024, file.getSize());
        assertEquals("Sample text", file.getOcrExtractedText());
        assertEquals(now, file.getUploadTime());
        assertEquals(now, file.getChangedTime());
    }

    @Test
    void testDefaultConstructorAndSetters() {
        Date now = new Date();
        File file = new File();
        file.setName("test.txt");
        file.setContentType("text/plain");
        file.setSize(1024);
        file.setOcrExtractedText("Sample text");
        file.setUploadTime(now);
        file.setChangedTime(now);

        assertEquals("test.txt", file.getName());
        assertEquals("text/plain", file.getContentType());
        assertEquals(1024, file.getSize());
        assertEquals("Sample text", file.getOcrExtractedText());
        assertEquals(now, file.getUploadTime());
        assertEquals(now, file.getChangedTime());
    }
}