package com.gk.portfolio.services.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface FileCloudService {
    void delete(String name);

    void upload(MultipartFile file);

    ByteArrayOutputStream download(String name);

    List<String> fileNames();
}
