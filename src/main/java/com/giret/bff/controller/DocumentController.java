package com.giret.bff.controller;

import com.giret.bff.model.Document;
import com.giret.bff.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequestMapping("/bff/document")
@CrossOrigin(origins = "*")
public class DocumentController {


    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/saveDocument")
    public ResponseEntity<Document> upload(@RequestParam("file") MultipartFile file,
                                           @RequestParam("recursoId") Long recursoId){

        return ResponseEntity.ok(documentService.saveDocument(file, recursoId));
    }

    @GetMapping("/by-resource/{id}")
    public ResponseEntity<List<Document>> findByRecursoId(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.findResourceById(id));
    }



}
