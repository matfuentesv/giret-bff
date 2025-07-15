package com.giret.bff.controller;

import com.giret.bff.model.Resource;
import com.giret.bff.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ResourceControllerTest {

    private ResourceService resourceService;
    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        resourceService = mock(ResourceService.class);
        resourceController = new ResourceController(resourceService);
    }

    @Test
    void testFindAllResources() {
        Resource r = Resource.builder().idRecurso(1L).estado("activo").build();
        when(resourceService.findAllResource()).thenReturn(List.of(r));

        ResponseEntity<List<Resource>> response = resourceController.findAllResources();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getIdRecurso());
        verify(resourceService, times(1)).findAllResource();
    }

    @Test
    void testFindResourceById() {
        Resource r = Resource.builder().idRecurso(2L).estado("mantenimiento").build();
        when(resourceService.findResourceById(2L)).thenReturn(r);

        ResponseEntity<Resource> response = resourceController.findResourceById(2L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getIdRecurso());
        verify(resourceService, times(1)).findResourceById(2L);
    }

    @Test
    void testSaveResource() {
        Resource request = Resource.builder().modelo("PC").estado("activo").build();
        Resource saved = Resource.builder().idRecurso(3L).modelo("PC").estado("activo").build();

        when(resourceService.saveResource(request)).thenReturn(saved);

        ResponseEntity<Resource> response = resourceController.saveResource(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getIdRecurso());
        verify(resourceService, times(1)).saveResource(request);
    }

    @Test
    void testUpdateResource() {
        Resource request = Resource.builder().modelo("Laptop").estado("mantenimiento").build();
        Resource updated = Resource.builder().idRecurso(4L).modelo("Laptop").estado("mantenimiento").build();

        when(resourceService.updateResource(4L, request)).thenReturn(updated);

        ResponseEntity<Resource> response = resourceController.updateResource(4L, request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(4L, response.getBody().getIdRecurso());
        verify(resourceService, times(1)).updateResource(4L, request);
    }

    @Test
    void testDeleteResource() {
        when(resourceService.deleteResource(5L)).thenReturn(true);

        ResponseEntity<Boolean> response = resourceController.deleteResource(5L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(Boolean.TRUE, response.getBody());
        verify(resourceService, times(1)).deleteResource(5L);
    }

    @Test
    void testCountResources() {
        when(resourceService.countResources()).thenReturn(10L);

        ResponseEntity<Long> response = resourceController.countResources();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(10L, response.getBody());
        verify(resourceService, times(1)).countResources();
    }





    @Test
    void testFindByEstado() {
        Resource r = Resource.builder().idRecurso(6L).estado("activo").build();
        when(resourceService.findByEstado("activo")).thenReturn(List.of(r));

        ResponseEntity<List<Resource>> response = resourceController.findByEstado("activo");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("activo", response.getBody().get(0).getEstado());
        verify(resourceService, times(1)).findByEstado("activo");
    }

    @Test
    void testSearchResource() {
        Resource r = Resource.builder().idRecurso(7L).modelo("HP").build();
        when(resourceService.searchResource("HP")).thenReturn(List.of(r));

        ResponseEntity<List<Resource>> response = resourceController.searchResource("HP");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("HP", response.getBody().get(0).getModelo());
        verify(resourceService, times(1)).searchResource("HP");
    }



    @Test
    void testCountByEstado() {
        Object[] estadoCount = {"activo", 5L};

        List<Object[]> estadoList = Collections.singletonList(estadoCount);

        when(resourceService.countByEstado()).thenReturn(estadoList);

        ResponseEntity<List<Object[]>> response = resourceController.countByEstado();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("activo", response.getBody().get(0)[0]);
        assertEquals(5L, response.getBody().get(0)[1]);

        verify(resourceService, times(1)).countByEstado();
    }



}