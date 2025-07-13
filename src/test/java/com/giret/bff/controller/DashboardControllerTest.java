package com.giret.bff.controller;


import com.giret.bff.model.DashboardPanel;
import com.giret.bff.model.PorcentajeRecurso;
import com.giret.bff.model.PrestamoPorVencer;
import com.giret.bff.service.DashboardServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DashboardControllerTest {

    private DashboardServices dashboardServices;
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        dashboardServices = mock(DashboardServices.class);
        dashboardController = new DashboardController(dashboardServices);
    }

    @Test
    void testFindAllResources() {
        DashboardPanel panel = DashboardPanel.builder()
                .recursosTotales(10L)
                .recursosPrestados(3L)
                .recursosMantenimiento(2L)
                .recursosEliminado(1L)
                .build();

        when(dashboardServices.getDashboardPanel()).thenReturn(panel);

        ResponseEntity<DashboardPanel> response = dashboardController.findAllResources();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(10L, response.getBody().getRecursosTotales());
        verify(dashboardServices, times(1)).getDashboardPanel();
    }

    @Test
    void testCountByEstadoConPorcentaje() {
        PorcentajeRecurso porcentaje = PorcentajeRecurso.builder()
                .estado("activo")
                .porcentaje(50.0)
                .cantidad(5L)
                .build();

        when(dashboardServices.countByEstadoConPorcentaje()).thenReturn(List.of(porcentaje));

        ResponseEntity<List<PorcentajeRecurso>> response = dashboardController.countByEstadoConPorcentaje();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("activo", response.getBody().get(0).getEstado());
        verify(dashboardServices, times(1)).countByEstadoConPorcentaje();
    }

    @Test
    void testPrestamosPorVencer() {
        PrestamoPorVencer prestamo = PrestamoPorVencer.builder()
                .prestamoId(1L)
                .mensajeVencimiento("Vence Hoy")
                .fechaDevolucion("2025-07-14")
                .solicitadoPor("Juan Pérez")
                .build();

        when(dashboardServices.getLoansDue()).thenReturn(List.of(prestamo));

        ResponseEntity<List<PrestamoPorVencer>> response = dashboardController.prestamosPorVencer();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Vence Hoy", response.getBody().get(0).getMensajeVencimiento());
        verify(dashboardServices, times(1)).getLoansDue();
    }
}
