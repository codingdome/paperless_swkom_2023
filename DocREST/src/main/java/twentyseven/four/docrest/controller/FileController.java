package twentyseven.four.docrest.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import twentyseven.four.docrest.model.File;
import twentyseven.four.docrest.service.FileService;
import twentyseven.four.docrest.service.MessagingService;
import twentyseven.four.docrest.service.MinioService;
import twentyseven.four.docrest.service.SearchService;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private SearchService searchService;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        try {
            //LOGGING
            logger.info("--- GET ALL --- ");

            List<File> files = fileService.getAll();
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            //LOGGING
            logger.error("Error occurred while fetching files", e);
            return ResponseEntity.internalServerError().body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        java.io.File serverFile = null;
        try {
            //LOGGING
            logger.info("--- RECEIVED ---: " + multipartFile.getOriginalFilename());

            // [ 1 ]
            //SAVE FILE TO SERVER FILE SYSTEM
            serverFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());

            //LOGGING
            logger.info("--- SERVERFILE ---: " + serverFile.getName());

            try (FileOutputStream fos = new FileOutputStream(serverFile)) {
                fos.write(multipartFile.getBytes());
            }

            // [ 2 ]
            // UPLOAD FILE TO MINIO
            minioService.upload(multipartFile, serverFile);

            // [ 3 ]
            // UPLOAD FILE TO POSTGRESQL
            File file = fileService.save(
                    File.builder()
                            .name(serverFile.getName())
                            .size(serverFile.length())
                            .contentType(multipartFile.getContentType())
                            .uploadTime(new Date())
                            .changedTime(new Date())
                            .ocrExtractedText("await_ocr")
                            .build()
            );

            // [ 4 ]
            // SENDING MESSAGE TO MESSAGE MQ
            messagingService.send(multipartFile.getOriginalFilename());

            // [ 5 ]
            // IF EVERYTHING WORKED - RESPOND TO FE WITH POSTGRESQL RESPONSE FILE

            //LOGGING
            logger.info("--- RESPONDING WITH ---: " + file.getName());

            return ResponseEntity.ok(file);

        } catch (Exception e) {
            // [ X ]
            // PRINT ERRORS
            e.printStackTrace();

            // [ X ]
            // IF POSTGRESQL FAILED REMOVE MINIO FILE
            if (serverFile != null && serverFile.exists()) {
                minioService.delete(serverFile.getName());
            }

            // [ X ]
            // RESPOND TO FE WITH ERROR MESSAGE
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + e.getMessage());
        } finally {
            // [ X ]
            // CLEAN UP SERVER FILE
            if (serverFile != null && serverFile.exists() && !serverFile.delete()) {
                System.err.println("Failed to delete temporary file: " + serverFile.getAbsolutePath());
            }
        }
    }

    @GetMapping("/get/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            //LOGGING
            logger.info("--- GET FILE ---: " + filename);

            Pair<InputStream, String> fileData = minioService.get(filename);
            if (fileData == null || fileData.getFirst() == null) {
                return ResponseEntity.notFound().build();
            }

            InputStream fileStream = fileData.getFirst();
            String contentType = fileData.getSecond();

            InputStreamResource resource = new InputStreamResource(fileStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            //LOGGING
            logger.error("Error occurred while fetching file: " + filename, e);
            return ResponseEntity.internalServerError().body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/delete/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        logger.info("--- DELETE --- " + filename);
        try {
            // Delete from PostgreSQL
            fileService.delete(filename);

            // Delete from MinIO
            minioService.delete(filename);

            // Delete from ElasticSearch
            searchService.deleteByName(filename);


            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            // Handle exceptions, e.g., file not found, MinIO errors, etc.
            return ResponseEntity.internalServerError().body("Error deleting file: " + e.getMessage());
        }
    }
}
