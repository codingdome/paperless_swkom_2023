package twentyseven.four.docrest.service;

import twentyseven.four.docrest.model.File;

import java.util.List;

public interface SearchService {

    List<File> findByContent(String content);

    List<File> findByName(String name);

    List<File> search(String query);

    void deleteByName(String filename);
}
