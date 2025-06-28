package com.giret.bff.service;


import com.giret.bff.client.ResourceClient;
import com.giret.bff.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    ResourceClient  resourceClient;


    @Override
    public List<Resource> findAllResource() {
        return resourceClient.findAllResource();
    }

    @Override
    public Resource findResourceById(Long id) {
        return resourceClient.findResourceById(id);
    }

    @Override
    public Resource saveResource(Resource resource) {
        return resourceClient.saveResource(resource);
    }

    @Override
    public Resource updateResource(Resource resource) {
        return resourceClient.updateResource(resource);
    }

    @Override
    public Boolean deleteResource(Long id) {
        return resourceClient.deleteResource(id);
    }

    @Override
    public Long countResources() {
        return resourceClient.countResources();
    }

    @Override
    public List<Object[]> countByEstado() {
        return resourceClient.countByEstado();
    }

    @Override
    public List<Resource> findByEstado(String state) {
        return resourceClient.countByEstado(state);
    }

    @Override
    public List<Resource> searchResource(String term) {
        return resourceClient.searchResource(term);
    }
}
