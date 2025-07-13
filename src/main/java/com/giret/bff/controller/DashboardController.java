package com.giret.bff.controller;

import com.giret.bff.model.DashboardPanel;
import com.giret.bff.model.PorcentajeRecurso;
import com.giret.bff.model.PrestamoPorVencer;
import com.giret.bff.service.DashboardServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/bff/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {


    private final DashboardServices dashboardServices;

    public DashboardController(DashboardServices dashboardServices) {
        this.dashboardServices = dashboardServices;
    }

    @GetMapping("/findAll")
    public ResponseEntity<DashboardPanel> findAllResources() {
        return ResponseEntity.ok(dashboardServices.getDashboardPanel());
    }

    @GetMapping("/countByEstadoConPorcentaje")
    public ResponseEntity<List<PorcentajeRecurso>> countByEstadoConPorcentaje() {
        return ResponseEntity.ok(dashboardServices.countByEstadoConPorcentaje());
    }

    @GetMapping("/findLoanDue")
    public ResponseEntity<List<PrestamoPorVencer>> prestamosPorVencer() {
        return ResponseEntity.ok(dashboardServices.getLoansDue());
    }

}
