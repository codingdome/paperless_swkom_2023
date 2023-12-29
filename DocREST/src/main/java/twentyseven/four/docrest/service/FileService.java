package twentyseven.four.docrest.service;

import twentyseven.four.docrest.model.File;

import java.util.List;
import java.util.Optional;

public interface FileService {

    File save(File file);

    List<File> getAll();

    void delete(String name);

    File findByName(String name);
}
