package com.gk.portfolio.services;

import com.gk.portfolio.exceptions.ExternalServiceUnavailableException;
import com.gk.portfolio.services.api.FileCloudService;
import com.jlefebure.spring.boot.minio.MinioException;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class MinioCloudService implements FileCloudService {
    private static final Logger logger = LoggerFactory.getLogger(MinioCloudService.class);

    @Autowired
    MinioCloudClient minioCloudClient;

    @PostConstruct
    public void init() {
        createBucketIfNeeded();
    }

    @Override
    public List<String> fileNames() {
        try {
            return minioCloudClient.list()
                    .parallelStream()
                    .filter(item -> !item.isDir())
                    .map(Item::objectName)
                    .collect(Collectors.toList());
        } catch (MinioException ex) {
            logger.error(format("Error in getting files %s", ex.getMessage()));
            throw new ExternalServiceUnavailableException(format("Minio service problem. %s", ex.getMessage()));
        }
    }

    @Override
    public ByteArrayOutputStream download(String name) {
        InputStream is;
        try {
            is = minioCloudClient.get(Paths.get(name));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }

            return baos;
        } catch (MinioException ex) {
            logger.error(format("Error in getting files %s", ex.getMessage()));
            throw new ExternalServiceUnavailableException(format("Minio service problem. %s", ex.getMessage()));
        } catch (IOException ex) {
            logger.error(format("Error in input/output files %s", ex.getMessage()));
        }
        return null;
    }


    @Override
    public void upload(MultipartFile file) {
        Path path = Paths.get(file.getOriginalFilename());
        Map<String, String> header = new HashMap<>();
        header.put("X-Incident-Id", "C918371984");
        try {
            minioCloudClient.upload(path, file.getInputStream(), file.getContentType());
        } catch (MinioException e) {
            logger.error(format("Error in uploading files %s", e.getMessage()));
            throw new ExternalServiceUnavailableException("The file cannot be upload into Minio please try later.");
        } catch (IOException e) {
            logger.error(format("The file cannot be read %s", e.getMessage()));
        }
    }

    @Override
    public void delete(String name) {
        Path path = Paths.get(name);
        try {
            minioCloudClient.remove(path);
        } catch (MinioException e) {
            logger.error(format("Error in delete file from minio %s", e.getMessage()));
            throw new ExternalServiceUnavailableException(format("Minio service problem. %s", e.getMessage()));
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void createBucketIfNeeded() {
        try {
            minioCloudClient.resolveBucket();
        } catch (MinioException e) {
            logger.error(format("Error in creating bucket in minio %s", e.getMessage()));
        }
    }
}
