package com.giret.bff.service.impl;

import com.giret.bff.model.DashboardPanel;
import com.giret.bff.service.DashboardServices;
import com.giret.bff.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardServices {

    @Autowired
    ResourceService resourceService;

    @Override
    public DashboardPanel getDashboardPanel() {

        final Long recursosTotales = (long) resourceService.findAllResource().size();

        final Long recursosPrestados = resourceService.findAllResource()
                                          .stream()
                                          .filter(x-> x.getEstado().equalsIgnoreCase("prestado"))
                                          .count();
        final Long recursosMantenimiento = resourceService.findAllResource().stream()
                                            .filter(x-> x.getEstado().equalsIgnoreCase("mantenimiento"))
                                            .count();

        final Long recursosAtrasados = resourceService.findAllResource().stream()
                .filter(x-> x.getEstado().equalsIgnoreCase("atrasado"))
                .count();

        return  DashboardPanel.builder()
                .recursosTotales(recursosTotales)
                .recursosPrestados(recursosPrestados)
                .recursosMantenimiento(recursosMantenimiento)
                .recursosAtrasados(recursosAtrasados)
                .build();
    }
}
