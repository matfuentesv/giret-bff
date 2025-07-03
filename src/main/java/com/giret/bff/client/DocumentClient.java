package com.giret.bff.client;

import com.giret.bff.model.Document;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@FeignClient(name = "documentClient", url = "${api.giret.document.base.url}")
public interface DocumentClient {

    @PostMapping(value = "/api/saveDocument",consumes = "multipart/form-data")
    Document saveDocument(@RequestParam("recursoId") Long recursoId,@RequestPart("file") MultipartFile file);


    @GetMapping(value = "/api/by-resource/{id}",produces = "application/json")
    List<Document> findResourceById(@PathVariable("id")Long id);


}
