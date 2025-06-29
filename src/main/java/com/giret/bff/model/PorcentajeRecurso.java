package com.giret.bff.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PorcentajeRecurso {

    private String estado;
    private Long cantidad;
    private Double porcentaje;


}
