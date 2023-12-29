package twentyseven.four.docrest.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docrest.persistence.repository.FileRepository;

@Service
@Transactional
public class DataSQLService {

    @Autowired
    FileRepository fileRepository;

    public void deleteAllFromSQLDatabase() {
        fileRepository.deleteAll();
    }
}
