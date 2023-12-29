package twentyseven.four.docrest.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import twentyseven.four.docrest.persistence.entity.FileEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Test
    @Rollback
    void saveNewFile() {
        long currentCount = fileRepository.count();
        FileEntity fileEntity = FileEntity.builder()
                .id(1L)
                .name("test")
                .ocrExtractedText("ocr text")
                .size(1024)
                .uploadTime(new Date())
                .changedTime(new Date())
                .contentType("application/pdf")
                .build();
        FileEntity savedEntity = fileRepository.save(fileEntity);
        assertEquals(currentCount + 1, fileRepository.count());
        fileRepository.delete(savedEntity);
    }

    @Test
    @Rollback
    public void testFindByName() {
        FileEntity entity = new FileEntity(null, "uniqueName.txt", "text/plain", 1024.0, "Sample text", new Date(), new Date());
        fileRepository.save(entity);
        Optional<FileEntity> foundEntity = fileRepository.findByName("uniqueName.txt");
        assertTrue(foundEntity.isPresent());
        assertEquals("uniqueName.txt", foundEntity.get().getName());
    }

    @Test
    @Rollback
    public void testSaveNewFileEntity() {
        FileEntity newEntity = new FileEntity(null, "newFile.txt", "text/plain", 500, "New text", new Date(), new Date());
        FileEntity savedEntity = fileRepository.save(newEntity);
        assertNotNull(savedEntity.getId());
    }

    @Test
    @Rollback
    public void testDeleteFileEntity() {
        FileEntity entity = new FileEntity(null, "deleteFile.txt", "text/plain", 500, "Delete text", new Date(), new Date());
        FileEntity savedEntity = fileRepository.save(entity);
        fileRepository.deleteById(savedEntity.getId());
        Optional<FileEntity> deletedEntity = fileRepository.findById(savedEntity.getId());
        assertFalse(deletedEntity.isPresent());
    }

    @Test
    @Rollback
    public void testFindById() {
        FileEntity entity = new FileEntity(null, "findByIdFile.txt", "text/plain", 500, "FindById text", new Date(), new Date());
        FileEntity savedEntity = fileRepository.save(entity);
        Optional<FileEntity> foundEntity = fileRepository.findById(savedEntity.getId());
        assertTrue(foundEntity.isPresent());
        assertEquals("findByIdFile.txt", foundEntity.get().getName());
    }

    @Test
    @Rollback
    public void testUpdateFileEntity() {
        FileEntity entity = new FileEntity(null, "updateFile.txt", "text/plain", 500, "Original text", new Date(), new Date());
        FileEntity savedEntity = fileRepository.save(entity);
        savedEntity.setName("updatedFile.txt");
        FileEntity updatedEntity = fileRepository.save(savedEntity);
        assertEquals("updatedFile.txt", updatedEntity.getName());
    }

    @Test
    @Rollback
    public void testFindAllFileEntities() {
        fileRepository.save(new FileEntity(null, "file1.txt", "text/plain", 500, "Text1", new Date(), new Date()));
        fileRepository.save(new FileEntity(null, "file2.txt", "text/plain", 600, "Text2", new Date(), new Date()));
        List<FileEntity> entities = fileRepository.findAll();
        assertTrue(entities.size() >= 2);
    }

    @Test
    @Rollback
    public void testFindByNameNotFound() {
        Optional<FileEntity> foundEntity = fileRepository.findByName("nonExistingFile.txt");
        assertFalse(foundEntity.isPresent());
    }

    @Test
    @Rollback
    public void testFileEntityLifecycle() {
        FileEntity entity = new FileEntity(null, "lifecycle.txt", "text/plain", 1024, "Lifecycle text", new Date(), new Date());
        FileEntity savedEntity = fileRepository.save(entity);
        Long entityId = savedEntity.getId();

        savedEntity.setName("updatedLifecycle.txt");
        FileEntity updatedEntity = fileRepository.save(savedEntity);
        assertEquals("updatedLifecycle.txt", updatedEntity.getName());

        fileRepository.deleteById(entityId);
        Optional<FileEntity> deletedEntity = fileRepository.findById(entityId);
        assertFalse(deletedEntity.isPresent());
    }
}