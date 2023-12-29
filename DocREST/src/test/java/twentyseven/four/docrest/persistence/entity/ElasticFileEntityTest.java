package twentyseven.four.docrest.persistence.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElasticFileEntityTest {

    @Test
    void testObjectCreationAndFieldAccess() {
        ElasticFileEntity entity = new ElasticFileEntity("file1", "Hello, world!");

        assertEquals("file1", entity.getName());
        assertEquals("Hello, world!", entity.getContent());
    }

    @Test
    void testBuilderPatternFunctionality() {
        ElasticFileEntity entity = ElasticFileEntity.builder()
                .name("file1")
                .content("Hello, world!")
                .build();

        assertEquals("file1", entity.getName());
        assertEquals("Hello, world!", entity.getContent());
    }

    @Test
    void testDefaultConstructorAndSetters() {
        ElasticFileEntity entity = new ElasticFileEntity();
        entity.setName("file1");
        entity.setContent("Hello, world!");

        assertEquals("file1", entity.getName());
        assertEquals("Hello, world!", entity.getContent());
    }

}