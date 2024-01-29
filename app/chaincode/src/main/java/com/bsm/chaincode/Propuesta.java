package com.bsm.chaincode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Propuesta {
    private String nombre;
    private String detallePropuesta;
    private int idPropuesta;
    private int votos;
}
