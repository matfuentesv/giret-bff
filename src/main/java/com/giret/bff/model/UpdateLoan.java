package com.giret.bff.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateLoan {

    private Long prestamoId;
    private Long recursoId;

}
