package com.gk.portfolio.controllers;

import com.gk.portfolio.services.MinioCloudService;
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
    private MinioCloudService minioService;

    @GetMapping
    public List<String> names() {
        return minioService.fileNames();
    }

    @GetMapping("/{name:.+}")
    public ResponseEntity<byte[]> getObject(@PathVariable("name") String object) {
        ByteArrayOutputStream downloadInputStream = minioService.download(object);

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
        minioService.upload(file);
    }

    @DeleteMapping("/{path}")
    public void delete(@PathVariable("path") String name) {
        minioService.delete(name);
    }
}