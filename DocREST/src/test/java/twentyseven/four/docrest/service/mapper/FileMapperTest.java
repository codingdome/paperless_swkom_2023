package twentyseven.four.docrest.service.mapper;

import org.junit.jupiter.api.Test;
import twentyseven.four.docrest.model.File;
import twentyseven.four.docrest.persistence.entity.FileEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FileMapperTest {

    private final FileMapper mapper = new FileMapper(); // Assuming FileMapper has no dependencies

    @Test
    public void shouldMapEntityToDto() {
        FileEntity entity = new FileEntity(1L, "document.pdf", "application/pdf", 123456, "Sample OCR Text", new Date(), new Date());
        File dto = mapper.fromEntity(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getContentType(), dto.getContentType());
        assertEquals(entity.getSize(), dto.getSize());
        assertEquals(entity.getOcrExtractedText(), dto.getOcrExtractedText());
        assertEquals(entity.getUploadTime(), dto.getUploadTime());
        assertEquals(entity.getChangedTime(), dto.getChangedTime());
    }

    @Test
    public void shouldMapDtoToEntity() {
        File dto = new File("document.pdf", "application/pdf", 123456, "Sample OCR Text", new Date(), new Date());
        FileEntity entity = mapper.toEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getContentType(), entity.getContentType());
        assertEquals(dto.getSize(), entity.getSize());
        assertEquals(dto.getOcrExtractedText(), entity.getOcrExtractedText());
        assertEquals(dto.getUploadTime(), entity.getUploadTime());
        assertEquals(dto.getChangedTime(), entity.getChangedTime());
    }

    @Test
    public void shouldReturnNullWhenEntityIsNull() {
        assertNull(mapper.fromEntity((FileEntity) null));
    }

    @Test
    public void shouldReturnNullWhenDtoIsNull() {
        assertNull(mapper.toEntity((File) null));
    }

    @Test
    public void shouldMapEntityToDtoAndBack() {
        FileEntity originalEntity = new FileEntity(1L, "document.pdf", "application/pdf", 123456, "Sample OCR Text", new Date(), new Date());
        File dto = mapper.fromEntity(originalEntity);
        FileEntity mappedEntity = mapper.toEntity(dto);

        assertEquals(originalEntity.getName(), mappedEntity.getName());
        assertEquals(originalEntity.getContentType(), mappedEntity.getContentType());
        assertEquals(originalEntity.getSize(), mappedEntity.getSize());
        assertEquals(originalEntity.getOcrExtractedText(), mappedEntity.getOcrExtractedText());
        assertEquals(originalEntity.getUploadTime(), mappedEntity.getUploadTime());
        assertEquals(originalEntity.getChangedTime(), mappedEntity.getChangedTime());
    }

}