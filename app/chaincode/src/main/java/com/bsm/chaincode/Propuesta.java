package com.bsm.chaincode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Propuesta {
    private String nombre;
    private String detallePropuesta;
    private int idPropuesta;
    private int votos;
}
