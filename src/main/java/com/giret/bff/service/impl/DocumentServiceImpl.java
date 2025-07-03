package com.giret.bff.service.impl;

import com.giret.bff.client.DocumentClient;
import com.giret.bff.model.Document;
import com.giret.bff.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentClient documentClient;


    @Override
    public Document saveDocument(MultipartFile file, Long recursoId) {
        return documentClient.saveDocument(recursoId,file);
    }

    @Override
    public List<Document> findResourceById(Long id) {
        return documentClient.findResourceById(id);
    }
}
