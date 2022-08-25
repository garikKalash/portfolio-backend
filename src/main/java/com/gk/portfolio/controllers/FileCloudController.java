package com.gk.portfolio.controllers;

import com.gk.portfolio.services.S3CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
public class FileCloudController {

    @Autowired
    private S3CloudService s3CloudService;

    @GetMapping
    public List<String> names() {
        return s3CloudService.fileNames();
    }

    @GetMapping("/buckets")
    public List<String> bucketsNames() {
        return s3CloudService.getBucketsList();
    }


    @GetMapping("/{name:.+}")
    public ResponseEntity<byte[]> getObject(@PathVariable("name") String object) {
        ByteArrayOutputStream downloadInputStream = s3CloudService.download(object);

        return ResponseEntity.ok()
                .contentType(contentType(object))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + object + "\"")
                .body(downloadInputStream.toByteArray());
    }

    private MediaType contentType(String keyname) {
        String[] arr = keyname.split("\\.");
        String type = arr[arr.length - 1];
        switch (type) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @PostMapping
    public void upload(@RequestParam("file") MultipartFile file) {
        s3CloudService.upload(file);
    }

    @DeleteMapping("/{path}")
    public void delete(@PathVariable("path") String name) {
        s3CloudService.delete(name);
    }
}