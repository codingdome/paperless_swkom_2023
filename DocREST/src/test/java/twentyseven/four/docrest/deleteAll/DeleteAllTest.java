package twentyseven.four.docrest.deleteAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import twentyseven.four.docrest.util.DataSQLService;

@SpringBootTest
public class DeleteAllTest {

    @Autowired
    DataSQLService dataSQLService;

    @Test
    void deleteAll() {
        dataSQLService.deleteAllFromSQLDatabase();
    }
}
