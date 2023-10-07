package org.house.got.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DataService {
    String populateData(MultipartFile file) throws IOException;
}
