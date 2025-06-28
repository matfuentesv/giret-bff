package com.giret.bff.service;

import com.giret.bff.model.Resource;
import java.util.List;


public interface ResourceService {


    List<Resource> findAllResource();
    Resource findResourceById(Long id);
    Resource saveResource(Resource resource);
    Resource updateResource(Long id,Resource resource);
    Boolean deleteResource(Long id);
    Long countResources();
    List<Object[]> countByEstado();
    List<Resource> findByEstado(String state);
    List<Resource> searchResource(String term);



}
