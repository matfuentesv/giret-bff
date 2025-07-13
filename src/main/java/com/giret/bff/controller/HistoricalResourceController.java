package com.giret.bff.controller;

import com.giret.bff.model.HistoricalResource;
import com.giret.bff.service.HistoricalResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff/historical-resource")
@CrossOrigin(origins = "*")
public class HistoricalResourceController {


    private final HistoricalResourceService historicalResourceService;

    public HistoricalResourceController(HistoricalResourceService historicalResourceService) {
        this.historicalResourceService = historicalResourceService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<HistoricalResource>> findAllHistoricalResource() {
        return ResponseEntity.ok(historicalResourceService.findAllHistoricalResource());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<List<HistoricalResource>> findResourceById(@PathVariable Long id) {
        return ResponseEntity.ok(historicalResourceService.findHistoricalByResourceId(id));
    }

    @PostMapping("/save")
    public ResponseEntity<HistoricalResource> saveHistoricalResource(@RequestBody HistoricalResource historicalResource) {
        return ResponseEntity.ok(historicalResourceService.saveHistoricalResource(historicalResource));
    }


}
