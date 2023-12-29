package twentyseven.four.docrest.persistence.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileEntityTest {

    @Test
    void testObjectCreationAndFieldAccess() {
        Date now = new Date();
        FileEntity entity = new FileEntity(1L, "test.txt", "text/plain", 1024.0, "Sample text", now, now);

        assertEquals(1L, entity.getId());
        assertEquals("test.txt", entity.getName());
        assertEquals("text/plain", entity.getContentType());
        assertEquals(1024.0, entity.getSize());
        assertEquals("Sample text", entity.getOcrExtractedText());
        assertEquals(now, entity.getUploadTime());
        assertEquals(now, entity.getChangedTime());
    }

    @Test
    void testBuilderPatternFunctionality() {
        Date now = new Date();
        FileEntity entity = FileEntity.builder()
                .id(1L)
                .name("test.txt")
                .contentType("text/plain")
                .size(1024.0)
                .ocrExtractedText("Sample text")
                .uploadTime(now)
                .changedTime(now)
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("test.txt", entity.getName());
        assertEquals("text/plain", entity.getContentType());
        assertEquals(1024.0, entity.getSize());
        assertEquals("Sample text", entity.getOcrExtractedText());
        assertEquals(now, entity.getUploadTime());
        assertEquals(now, entity.getChangedTime());
    }

    @Test
    void testDefaultConstructorAndSetters() {
        Date now = new Date();
        FileEntity entity = new FileEntity();
        entity.setId(1L);
        entity.setName("test.txt");
        entity.setContentType("text/plain");
        entity.setSize(1024.0);
        entity.setOcrExtractedText("Sample text");
        entity.setUploadTime(now);
        entity.setChangedTime(now);

        assertEquals(1L, entity.getId());
        assertEquals("test.txt", entity.getName());
        assertEquals("text/plain", entity.getContentType());
        assertEquals(1024.0, entity.getSize());
        assertEquals("Sample text", entity.getOcrExtractedText());
        assertEquals(now, entity.getUploadTime());
        assertEquals(now, entity.getChangedTime());
    }


}