package com.example.docprotocol.services;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    public void deleteAll() {
        try {
            // Replace "files" with your bucket name
            String bucketName = "files";

            // Build ListObjectsArgs with the bucket name
            ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .build();

            // List all objects
            Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);

            // Iterate over each object and delete
            for (Result<Item> result : results) {
                Item item = result.get();
                RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(item.objectName())
                        .build();
                minioClient.removeObject(removeObjectArgs);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error deleting all objects from MinIO bucket", e);
        }
    }

    public int getObjectCount() {
        try {
            String bucketName = "files"; // Replace with your bucket name
            ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .build();

            Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);

            int count = 0;
            for (Result<Item> result : results) {
                result.get(); // Fetch the item to ensure it's counted
                count++;
            }

            return count;
        } catch (Exception e) {
            throw new RuntimeException("Error getting object count from MinIO bucket", e);
        }
    }

    public List<String> getAllFilenames() {
        try {
            String bucketName = "files"; // Replace with your bucket name
            ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .build();

            Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);

            List<String> filenames = new ArrayList<>();
            for (Result<Item> result : results) {
                Item item = result.get();
                filenames.add(item.objectName());
            }

            return filenames;
        } catch (Exception e) {
            throw new RuntimeException("Error getting filenames from MinIO bucket", e);
        }
    }



}
