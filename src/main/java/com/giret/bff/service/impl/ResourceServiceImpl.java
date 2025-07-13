package com.giret.bff.service.impl;


import com.giret.bff.client.HistoricalResourceClient;
import com.giret.bff.client.ResourceClient;
import com.giret.bff.model.HistoricalResource;
import com.giret.bff.model.Resource;
import com.giret.bff.service.ResourceService;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {


    private final ResourceClient  resourceClient;
    private final HistoricalResourceClient historicalResourceClient;


    public ResourceServiceImpl(ResourceClient resourceClient, HistoricalResourceClient historicalResourceClient) {
        this.resourceClient = resourceClient;
        this.historicalResourceClient = historicalResourceClient;
    }

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
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final ZoneId chileZoneId = ZoneId.of("America/Santiago");
        String fechaCambioEstado = ZonedDateTime.now(chileZoneId).format(formatter);
        final Resource r = resourceClient.saveResource(resource);
        final HistoricalResource historicalResource = HistoricalResource
                                                 .builder()
                                                 .recursoId(r.getIdRecurso())
                                                 .fechaCambioEstado(fechaCambioEstado)
                                                 .accion("Creacion")
                                                 .descripcion("creacion del recurso")
                                                 .build();
        historicalResourceClient.saveHistoricalResource(historicalResource);
        return r;
    }

    @Override
    public Resource updateResource(Long id,Resource resource) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final ZoneId chileZoneId = ZoneId.of("America/Santiago");
        String fechaCambioEstado = ZonedDateTime.now(chileZoneId).format(formatter);
        final Resource r = resourceClient.updateResource(id,resource);
        final HistoricalResource historicalResource = HistoricalResource
                .builder()
                .recursoId(r.getIdRecurso())
                .fechaCambioEstado(fechaCambioEstado)
                .accion("Actualizacion")
                .descripcion("Cambio de estado a " + r.getEstado())
                .build();
        historicalResourceClient.saveHistoricalResource(historicalResource);
        return r;
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
