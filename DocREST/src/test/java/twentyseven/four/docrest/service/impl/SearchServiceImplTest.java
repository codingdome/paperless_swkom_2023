package twentyseven.four.docrest.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import twentyseven.four.docrest.model.File;
import twentyseven.four.docrest.service.SearchService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchServiceImplTest {

    @Autowired
    SearchService searchService;


    @Test
    void testSearchByContent() {
        List<File> result = searchService.findByContent("Texte");
        for (File file: result
             ) {
            System.out.println(file.getName() + "---" + file.getSize());
        }
    }
}