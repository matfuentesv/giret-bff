package com.giret.bff.controller;

import com.giret.bff.model.Document;
import com.giret.bff.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/bff/document")
@CrossOrigin(origins = "*")
public class DocumentController {


    @Autowired
    DocumentService documentService;

    @PostMapping(value = "/saveDocument",consumes = "multipart/form-data")
    public ResponseEntity<Document> upload(@RequestParam("file") MultipartFile file,
                                           @RequestParam("recursoId") Long recursoId){

        return ResponseEntity.ok(documentService.saveDocument(file, recursoId));
    }

    @GetMapping("/by-resource/{id}")
    public ResponseEntity<List<Document>> findByRecursoId(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.findResourceById(id));
    }



}
