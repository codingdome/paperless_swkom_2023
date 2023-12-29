package twentyseven.four.docservices.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twentyseven.four.docservices.service.FetchFileService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class FetchFileServiceImpl implements FetchFileService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public FetchFileServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public File getFile(String fileName) throws Exception {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build())) {
            Path tempDir = Files.createTempDirectory("minio_files_");
            File tempFile = new File(tempDir.toFile(), fileName);
            Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        }
    }
}
