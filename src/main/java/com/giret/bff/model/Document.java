package com.giret.bff.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {

    private Long id;
    private String nombreArchivo;
    private String url;
    private String tipoMime;
    private String fechaCarga;
    private Long recursoId;

}
