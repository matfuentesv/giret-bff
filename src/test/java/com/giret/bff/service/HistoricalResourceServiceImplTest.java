package com.giret.bff.service;

import com.giret.bff.client.HistoricalResourceClient;
import com.giret.bff.model.HistoricalResource;
import com.giret.bff.service.impl.HistoricalResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class HistoricalResourceServiceImplTest {

    private HistoricalResourceClient historicalResourceClient;
    private HistoricalResourceServiceImpl historicalResourceService;

    @BeforeEach
    void setUp() {
        historicalResourceClient = mock(HistoricalResourceClient.class);
        historicalResourceService = new HistoricalResourceServiceImpl(historicalResourceClient);
    }

    @Test
    void testFindAllHistoricalResource() {
        HistoricalResource hr = HistoricalResource.builder()
                .idHistorial(1L)
                .recursoId(10L)
                .accion("Creacion")
                .descripcion("Recurso creado")
                .build();

        when(historicalResourceClient.findAllHistoricalResource()).thenReturn(List.of(hr));

        List<HistoricalResource> result = historicalResourceService.findAllHistoricalResource();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getIdHistorial());

        verify(historicalResourceClient, times(1)).findAllHistoricalResource();
    }

    @Test
    void testFindHistoricalByResourceId() {
        HistoricalResource hr = HistoricalResource.builder()
                .idHistorial(2L)
                .recursoId(20L)
                .accion("Actualizacion")
                .descripcion("Estado actualizado")
                .build();

        when(historicalResourceClient.findHistoricalResource(20L)).thenReturn(List.of(hr));

        List<HistoricalResource> result = historicalResourceService.findHistoricalByResourceId(20L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(20L, result.get(0).getRecursoId());

        verify(historicalResourceClient, times(1)).findHistoricalResource(20L);
    }

    @Test
    void testSaveHistoricalResource() {
        HistoricalResource request = HistoricalResource.builder()
                .recursoId(30L)
                .accion("Eliminacion")
                .descripcion("Recurso eliminado")
                .build();

        HistoricalResource saved = HistoricalResource.builder()
                .idHistorial(3L)
                .recursoId(30L)
                .accion("Eliminacion")
                .descripcion("Recurso eliminado")
                .build();

        when(historicalResourceClient.saveHistoricalResource(request)).thenReturn(saved);

        HistoricalResource result = historicalResourceService.saveHistoricalResource(request);

        assertNotNull(result);
        assertEquals(3L, result.getIdHistorial());
        verify(historicalResourceClient, times(1)).saveHistoricalResource(request);
    }
}
