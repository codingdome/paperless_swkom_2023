package twentyseven.four.docrest.service.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import twentyseven.four.docrest.service.MinioService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@Transactional
public class MinioServiceImpl implements MinioService {

    private static final Logger logger = Logger.getLogger(MinioServiceImpl.class);

    @Autowired
    private MinioClient minioClient;

    @Override
    public void upload(MultipartFile file, File serverFile) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            // LOGGING
            logger.info("--- UPLOADING --- " + serverFile.getName());

            UploadObjectArgs uArgs = UploadObjectArgs.builder()
                    .bucket("files")
                    .object(file.getOriginalFilename())
                    .filename(serverFile.getAbsolutePath())
                    .contentType(file.getContentType())
                    .build();
            minioClient.uploadObject(uArgs);

            // LOGGING
            logger.info("--- UPLOADED --- " + serverFile.getName());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String objectName) {
        try {
            // LOGGING
            logger.info("--- REMOVING --- " + objectName);

            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket("files")
                    .object(objectName)
                    .build();
            minioClient.removeObject(removeObjectArgs);

            // LOGGING
            logger.info("--- REMOVED --- " + objectName);

        } catch (Exception e) {
            throw new RuntimeException("Error deleting object from MinIO: " + objectName, e);
        }
    }

    @Override
    public Pair<InputStream, String> get(String filename) throws NoSuchAlgorithmException, InvalidKeyException, IOException, ServerException, InsufficientDataException, ErrorResponseException, InvalidResponseException, XmlParserException, InternalException {
        try {
            // LOGGING
            logger.info("--- RETRIEVING --- " + filename);

            // Retrieve the file
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket("files")
                    .object(filename)
                    .build();
            InputStream inputStream = minioClient.getObject(getObjectArgs);

            // Retrieve metadata to get content type
            StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                    .bucket("files")
                    .object(filename)
                    .build();
            StatObjectResponse stat = minioClient.statObject(statObjectArgs);
            String contentType = stat.contentType();

            // LOGGING
            logger.info("--- RETRIEVED --- " + filename + " with content type: " + contentType);

            return Pair.of(inputStream, contentType);
        } catch (Exception e) {
            logger.error("Error retrieving object from MinIO: " + filename, e);
            throw e; // or handle it as per your application's error handling strategy
        }
    }

}