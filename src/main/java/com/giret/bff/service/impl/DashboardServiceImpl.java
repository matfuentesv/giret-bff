package com.giret.bff.service.impl;

import com.giret.bff.model.*;
import com.giret.bff.service.DashboardServices;
import com.giret.bff.service.LoanServices;
import com.giret.bff.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardServices {

    @Autowired
    ResourceService resourceService;

    @Autowired
    LoanServices loanServices;

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

    @Override
    public List<PrestamoPorVencer> GetLoansDue() {

        final LocalDate hoy = LocalDate.now();

        // Calcula el último día de la semana (domingo)
        final LocalDate finSemana = hoy.with(java.time.DayOfWeek.SUNDAY);

        final List<Loan> loanList = loanServices.getAllLoans();
        List<PrestamoPorVencer> result = new ArrayList<>();

        for (Loan loan : loanList) {
            LocalDate fechaDev = LocalDate.parse(loan.getFechaDevolucion());


            if (!fechaDev.isBefore(hoy) && !fechaDev.isAfter(finSemana)) {

                long dias = ChronoUnit.DAYS.between(hoy, fechaDev);

                String mensaje;
                if (dias == 0) {
                    mensaje = "Vence Hoy";
                } else if (dias == 1) {
                    mensaje = "Vence Mañana";
                } else {
                    mensaje = "Vence en " + dias + " días";
                }

                PrestamoPorVencer p = PrestamoPorVencer.builder()
                        .prestamoId(loan.getIdPrestamo())
                        .recurso(resourceService.findResourceById(loan.getRecursoId()))
                        .solicitadoPor(loan.getSolicitante())
                        .mensajeVencimiento(mensaje)
                        .fechaDevolucion(loan.getFechaDevolucion())
                        .build();

                result.add(p);
            }
        }

        return result;
    }
}
