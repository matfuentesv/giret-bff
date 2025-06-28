package com.giret.bff.client;

import com.giret.bff.model.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "resourceClient", url = "${api.giret.resouce.base.url}")
public interface ResourceClient {

    @GetMapping(value = "/api/findAllResource",produces = "application/json")
    List<Resource> findAllResource();

    @GetMapping(value = "/api/findResourceById/{id}",produces = "application/json")
    Resource findResourceById(@PathVariable("id")Long id);

    @PostMapping(value = "/api/saveResource",produces = "application/json")
    Resource saveResource(@RequestBody Resource resource);

    @PutMapping(value = "/api/updateResource/{id}",produces = "application/json")
    Resource updateResource(@PathVariable("id")Long id, @RequestBody Resource resource);

    @DeleteMapping(value = "/api/deleteResource/{id}",produces = "application/json")
    Boolean deleteResource(@PathVariable ("id")Long id);

    @GetMapping(value = "/api/resource/count",produces = "application/json")
    Long countResources();

    @GetMapping(value = "/resource/countByEstado",produces = "application/json")
    List<Object[]> countByEstado();

    @GetMapping(value = "/resource/findByEstado/{state}",produces = "application/json")
    List<Resource> countByEstado(@PathVariable String state);

    @GetMapping(value = "/resource/search",produces = "application/json")
    List<Resource> searchResource(@RequestParam String term);




}
