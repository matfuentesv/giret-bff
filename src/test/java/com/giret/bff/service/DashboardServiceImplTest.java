package com.giret.bff.service;

import com.giret.bff.model.*;
import com.giret.bff.service.impl.DashboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DashboardServiceImplTest {

    private ResourceService resourceService;
    private LoanServices loanServices;
    private DashboardServiceImpl dashboardService;

    @BeforeEach
    void setUp() {
        resourceService = mock(ResourceService.class);
        loanServices = mock(LoanServices.class);
        dashboardService = new DashboardServiceImpl(resourceService, loanServices);
    }

    @Test
    void testGetDashboardPanel() {
        Resource r1 = Resource.builder().estado("prestado").build();
        Resource r2 = Resource.builder().estado("mantenimiento").build();
        Resource r3 = Resource.builder().estado("eliminado").build();
        Resource r4 = Resource.builder().estado("prestado").build();
        Resource r5 = Resource.builder().estado("activo").build();

        when(resourceService.findAllResource()).thenReturn(List.of(r1, r2, r3, r4, r5));

        DashboardPanel panel = dashboardService.getDashboardPanel();

        assertNotNull(panel);
        assertEquals(5L, panel.getRecursosTotales());
        assertEquals(2L, panel.getRecursosPrestados());
        assertEquals(1L, panel.getRecursosMantenimiento());
        assertEquals(1L, panel.getRecursosEliminado());

        verify(resourceService, times(4)).findAllResource();
    }

    @Test
    void testCountByEstadoConPorcentaje() {
        Resource r1 = Resource.builder().estado("activo").build();
        Resource r2 = Resource.builder().estado("activo").build();
        Resource r3 = Resource.builder().estado("mantenimiento").build();

        when(resourceService.findAllResource()).thenReturn(List.of(r1, r2, r3));

        List<PorcentajeRecurso> result = dashboardService.countByEstadoConPorcentaje();

        assertNotNull(result);
        assertEquals(2, result.size());
        PorcentajeRecurso activo = result.stream().filter(p -> p.getEstado().equals("activo")).findFirst().orElse(null);
        assertNotNull(activo);
        assertEquals(2L, activo.getCantidad());
        assertEquals(66.7, activo.getPorcentaje(), 0.1);

        PorcentajeRecurso mant = result.stream().filter(p -> p.getEstado().equals("mantenimiento")).findFirst().orElse(null);
        assertNotNull(mant);
        assertEquals(1L, mant.getCantidad());
        assertEquals(33.3, mant.getPorcentaje(), 0.1);

        verify(resourceService, times(1)).findAllResource();
    }

    @Test
    void testGetLoansDue() {
        LocalDate today = LocalDate.now();
        LocalDate sunday = today.with(java.time.DayOfWeek.SUNDAY);

        Loan loan1 = Loan.builder()
                .idPrestamo(1L)
                .recursoId(100L)
                .fechaDevolucion(today.toString())
                .solicitante("Juan")
                .build();

        Loan loan2 = Loan.builder()
                .idPrestamo(2L)
                .recursoId(200L)
                .fechaDevolucion(sunday.toString())
                .solicitante("Maria")
                .build();

        when(loanServices.getAllLoans()).thenReturn(List.of(loan1, loan2));


        List<PrestamoPorVencer> result = dashboardService.getLoansDue();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(p -> p.getMensajeVencimiento().contains("Hoy")));
        verify(loanServices, times(1)).getAllLoans();
        verify(resourceService, atLeastOnce()).findResourceById(anyLong());
    }
}
