package com.giret.bff.service;

import com.giret.bff.client.HistoricalResourceClient;
import com.giret.bff.client.ResourceClient;
import com.giret.bff.model.HistoricalResource;
import com.giret.bff.model.Resource;
import com.giret.bff.service.impl.ResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ResourceServiceImplTest {

    private ResourceClient resourceClient;
    private HistoricalResourceClient historicalResourceClient;
    private ResourceServiceImpl resourceService;

    @BeforeEach
    void setUp() {
        resourceClient = mock(ResourceClient.class);
        historicalResourceClient = mock(HistoricalResourceClient.class);
        resourceService = new ResourceServiceImpl(resourceClient, historicalResourceClient);
    }

    @Test
    void testFindAllResource() {
        Resource r = Resource.builder().idRecurso(1L).build();
        when(resourceClient.findAllResource()).thenReturn(List.of(r));

        List<Resource> result = resourceService.findAllResource();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(resourceClient, times(1)).findAllResource();
    }

    @Test
    void testFindResourceById() {
        Resource r = Resource.builder().idRecurso(2L).build();
        when(resourceClient.findResourceById(2L)).thenReturn(r);

        Resource result = resourceService.findResourceById(2L);

        assertNotNull(result);
        assertEquals(2L, result.getIdRecurso());
        verify(resourceClient, times(1)).findResourceById(2L);
    }

    @Test
    void testSaveResource() {
        Resource input = Resource.builder().modelo("PC").estado("activo").build();
        Resource saved = Resource.builder().idRecurso(3L).modelo("PC").estado("activo").build();

        when(resourceClient.saveResource(input)).thenReturn(saved);

        Resource result = resourceService.saveResource(input);

        assertNotNull(result);
        assertEquals(3L, result.getIdRecurso());
        verify(resourceClient, times(1)).saveResource(input);
        verify(historicalResourceClient, times(1)).saveHistoricalResource(any(HistoricalResource.class));
    }

    @Test
    void testUpdateResource() {
        Resource input = Resource.builder().modelo("Laptop").estado("mantenimiento").build();
        Resource updated = Resource.builder().idRecurso(4L).modelo("Laptop").estado("mantenimiento").build();

        when(resourceClient.updateResource(4L, input)).thenReturn(updated);

        Resource result = resourceService.updateResource(4L, input);

        assertNotNull(result);
        assertEquals(4L, result.getIdRecurso());
        verify(resourceClient, times(1)).updateResource(4L, input);
        verify(historicalResourceClient, times(1)).saveHistoricalResource(any(HistoricalResource.class));
    }

    @Test
    void testDeleteResource() {
        when(resourceClient.deleteResource(5L)).thenReturn(true);

        Boolean result = resourceService.deleteResource(5L);

        assertTrue(result);
        verify(resourceClient, times(1)).deleteResource(5L);
    }

    @Test
    void testCountResources() {
        when(resourceClient.countResources()).thenReturn(10L);

        Long count = resourceService.countResources();

        assertEquals(10L, count);
        verify(resourceClient, times(1)).countResources();
    }

    @Test
    void testCountByEstado() {
        Object[] estadoCount = new Object[]{"activo", 5L};
        List<Object[]> estadoList = Collections.singletonList(estadoCount);
        when(resourceClient.countByEstado()).thenReturn(estadoList);

        List<Object[]> result = resourceService.countByEstado();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("activo", result.get(0)[0]);
        verify(resourceClient, times(1)).countByEstado();
    }

    @Test
    void testFindByEstado() {
        Resource r = Resource.builder().idRecurso(6L).estado("activo").build();
        when(resourceClient.countByEstado("activo")).thenReturn(List.of(r));

        List<Resource> result = resourceService.findByEstado("activo");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("activo", result.get(0).getEstado());
        verify(resourceClient, times(1)).countByEstado("activo");
    }

    @Test
    void testSearchResource() {
        Resource r = Resource.builder().idRecurso(7L).modelo("HP").build();
        when(resourceClient.searchResource("HP")).thenReturn(List.of(r));

        List<Resource> result = resourceService.searchResource("HP");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("HP", result.get(0).getModelo());
        verify(resourceClient, times(1)).searchResource("HP");
    }
}
