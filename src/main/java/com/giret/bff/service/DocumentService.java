package com.giret.bff.service;

import com.giret.bff.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    Document saveDocument(MultipartFile file,Long recursoId);
    List<Document> findResourceById(Long id);


}
