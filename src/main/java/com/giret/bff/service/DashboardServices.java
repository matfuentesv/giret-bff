package com.giret.bff.service;

import com.giret.bff.model.DashboardPanel;
import com.giret.bff.model.PorcentajeRecurso;

import java.util.List;

public interface DashboardServices {

    DashboardPanel getDashboardPanel();
    List<PorcentajeRecurso> countByEstadoConPorcentaje();
}
