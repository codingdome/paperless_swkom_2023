package twentyseven.four.docservices.service;

import java.io.File;

public interface FetchFileService {
    File getFile(String fileName) throws Exception;
}