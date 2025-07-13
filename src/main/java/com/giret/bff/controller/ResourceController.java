package com.giret.bff.controller;


import com.giret.bff.model.Resource;
import com.giret.bff.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff/resource")
@CrossOrigin(origins = "*")
public class ResourceController {


    private final ResourceService resourceService;


    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Resource>> findAllResources() {
        return ResponseEntity.ok(resourceService.findAllResource());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Resource> findResourceById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.findResourceById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Resource> saveResource(@RequestBody Resource resource) {
        return ResponseEntity.ok(resourceService.saveResource(resource));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable("id")Long id,@RequestBody Resource resource) {
        return ResponseEntity.ok(resourceService.updateResource(id,resource));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteResource(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.deleteResource(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countResources() {
        return ResponseEntity.ok(resourceService.countResources());
    }

    @GetMapping("/countByEstado")
    public ResponseEntity<List<Object[]>> countByEstado() {
        return ResponseEntity.ok(resourceService.countByEstado());
    }

    @GetMapping("/findByEstado/{state}")
    public ResponseEntity<List<Resource>> findByEstado(@PathVariable String state) {
        return ResponseEntity.ok(resourceService.findByEstado(state));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Resource>> searchResource(@RequestParam String term) {
        return ResponseEntity.ok(resourceService.searchResource(term));
    }

}
