package twentyseven.four.docservices.service.impl;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docservices.service.OCRService;

import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class OCRServiceImpl implements OCRService {

    public final Tesseract tesseract = new Tesseract();

    @Override
    public String processImageOCR(File file) {
        try {
            // Adjust the path based on the Docker container's Tesseract installation
            tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
            tesseract.setLanguage("eng");
            String text = tesseract.doOCR(file);
            return text;
        } catch (TesseractException e) {
            System.out.println(e);
        }
        return "Error";
    }



    @Override
    public String processPdfOCR(File file) throws IOException {

        PDDocument document = Loader.loadPDF(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String content = pdfTextStripper.getText(document);

        document.close();
        return content;
    }
}
