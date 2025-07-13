package com.giret.bff.controller;

import com.giret.bff.model.HistoricalResource;
import com.giret.bff.service.HistoricalResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HistoricalResourceControllerTest {

    private HistoricalResourceService historicalResourceService;
    private HistoricalResourceController historicalResourceController;

    @BeforeEach
    void setUp() {
        historicalResourceService = mock(HistoricalResourceService.class);
        historicalResourceController = new HistoricalResourceController(historicalResourceService);
    }

    @Test
    void testFindAllHistoricalResource() {
        HistoricalResource hr = HistoricalResource.builder()
                .idHistorial(1L)
                .recursoId(100L)
                .accion("Creacion")
                .descripcion("Recurso creado")
                .build();

        when(historicalResourceService.findAllHistoricalResource()).thenReturn(List.of(hr));

        ResponseEntity<List<HistoricalResource>> response = historicalResourceController.findAllHistoricalResource();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getIdHistorial());
        verify(historicalResourceService, times(1)).findAllHistoricalResource();
    }

    @Test
    void testFindResourceById() {
        HistoricalResource hr = HistoricalResource.builder()
                .idHistorial(2L)
                .recursoId(200L)
                .accion("Actualizacion")
                .descripcion("Estado actualizado")
                .build();

        when(historicalResourceService.findHistoricalByResourceId(200L)).thenReturn(List.of(hr));

        ResponseEntity<List<HistoricalResource>> response = historicalResourceController.findResourceById(200L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(200L, response.getBody().get(0).getRecursoId());
        verify(historicalResourceService, times(1)).findHistoricalByResourceId(200L);
    }

    @Test
    void testSaveHistoricalResource() {
        HistoricalResource request = HistoricalResource.builder()
                .recursoId(300L)
                .accion("Eliminacion")
                .descripcion("Recurso eliminado")
                .build();

        HistoricalResource saved = HistoricalResource.builder()
                .idHistorial(3L)
                .recursoId(300L)
                .accion("Eliminacion")
                .descripcion("Recurso eliminado")
                .build();

        when(historicalResourceService.saveHistoricalResource(request)).thenReturn(saved);

        ResponseEntity<HistoricalResource> response = historicalResourceController.saveHistoricalResource(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3L, response.getBody().getIdHistorial());
        verify(historicalResourceService, times(1)).saveHistoricalResource(request);
    }
}
