package com.gk.portfolio.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.gk.portfolio.services.api.FileCloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
public class S3CloudService implements FileCloudService {

    @Autowired
    private AmazonS3 s3CloudClient;

    @Autowired
    private Bucket bucket;

    public void upload(MultipartFile multipartFile) {
        File file = convertMultipartFileToFile(multipartFile);
        String fileName = "new_" + multipartFile.getOriginalFilename();
       s3CloudClient.putObject(bucket.getName(), fileName, file);
       file.delete();

    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file");
        }
        return file;
    }

    public List<String> fileNames() {
        List<S3ObjectSummary> objectSummaries = s3CloudClient.listObjects(bucket.getName()).getObjectSummaries();
        return objectSummaries.stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());

    }

    public ByteArrayOutputStream download(String fileName) {
        S3Object s3Object = s3CloudClient.getObject(bucket.getName(), fileName);
        InputStream is = s3Object.getObjectContent();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }

            return baos;
        } catch (IOException ex) {
            log.error(format("Error in input/output files %s", ex.getMessage()));
        }
        return null;
    }

    public void delete(String fileName){
       s3CloudClient.deleteObject(bucket.getName(), fileName);

    }
    public List<String> getBucketsList(){

        return  s3CloudClient.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
    }




}
