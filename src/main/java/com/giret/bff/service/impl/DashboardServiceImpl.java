package com.giret.bff.service.impl;

import com.giret.bff.model.DashboardPanel;
import com.giret.bff.model.PorcentajeRecurso;
import com.giret.bff.model.Resource;
import com.giret.bff.service.DashboardServices;
import com.giret.bff.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<PorcentajeRecurso> countByEstadoConPorcentaje() {

        final List<Resource> resourceList = resourceService.findAllResource();
        Map<String, Long> counts = resourceList
                                  .stream()
                                  .collect(Collectors.groupingBy(Resource::getEstado, Collectors.counting()));

        long total = resourceList.size();


        List<PorcentajeRecurso> result = new ArrayList<>();
        counts.forEach((estado, cantidad) -> {
            double porcentaje = total > 0 ? (cantidad * 100.0) / total : 0.0;

            // 🔵 Redondear a 1 decimal
            porcentaje = Math.round(porcentaje * 10.0) / 10.0;

            PorcentajeRecurso p = PorcentajeRecurso.builder()
                    .estado(estado)
                    .porcentaje(porcentaje)
                    .cantidad(cantidad)
                    .build();
            result.add(p);
        });


        return result;
    }
}
