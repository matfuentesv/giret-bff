package com.giret.bff.model;

import lombok.Data;

@Data
public class HistoricalResource {


    private Long idHistorial;
    private Long recursoId;
    private String fechaCambioEstado;
    private String accion;
    private String descripcion;

}
