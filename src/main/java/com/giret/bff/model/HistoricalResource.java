package com.giret.bff.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoricalResource {


    private Long idHistorial;
    private Long recursoId;
    private String fechaCambioEstado;
    private String accion;
    private String descripcion;

}
