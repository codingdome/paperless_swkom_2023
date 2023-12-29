package twentyseven.four.docservices.service;

import java.io.File;
import java.io.IOException;

public interface OCRService {

    String processImageOCR(File file);

    String processPdfOCR(File file) throws IOException;
}
