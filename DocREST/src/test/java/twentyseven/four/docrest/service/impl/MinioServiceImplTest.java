package twentyseven.four.docrest.service.impl;

import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.StatObjectResponse;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;
import twentyseven.four.docrest.service.MinioService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class MinioServiceImplTest {

    @Autowired
    private MinioService minioService;

    @MockBean
    private MinioClient minioClient;

    @Test
    void testUploadSuccess() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("testfile.txt");
        when(file.getContentType()).thenReturn("text/plain"); // Set a content type

        // Create a temporary file
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit(); // Ensure the file is deleted after tests

        minioService.upload(file, tempFile);

        verify(minioClient).uploadObject(any(UploadObjectArgs.class));
    }

    @Test
    void testDeleteSuccess() throws Exception {
        minioService.delete("testfile.txt");
        verify(minioClient).removeObject(any());
    }

    @Test
    void testDeleteException() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        doThrow(new RuntimeException()).when(minioClient).removeObject(any());
        assertThrows(RuntimeException.class, () -> minioService.delete("testfile.txt"));
    }

    @Test
    void testUploadWithNullFile() {
        MultipartFile file = null;
        assertThrows(NullPointerException.class, () -> minioService.upload(file, new File("test.txt")));
    }

    @Test
    void testDeleteWithNullFilename() {
        assertThrows(RuntimeException.class, () -> minioService.delete(null));
    }

    @Test
    void testGetWithNullFilename() {
        assertThrows(IllegalArgumentException.class, () -> minioService.get(null));
    }


}