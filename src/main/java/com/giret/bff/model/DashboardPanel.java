package com.giret.bff.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardPanel {

    private Long recursosTotales;
    private Long recursosPrestados;
    private Long recursosMantenimiento;
    private Long recursosAtrasados;
}
