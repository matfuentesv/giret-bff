package com.giret.bff.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrestamoPorVencer {

    private Long prestamoId;
    private String solicitadoPor;
    private String mensajeVencimiento;
    private String fechaDevolucion;
    private Resource recurso;
}
