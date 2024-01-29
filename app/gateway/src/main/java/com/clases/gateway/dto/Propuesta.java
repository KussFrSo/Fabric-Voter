package com.clases.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Propuesta {
    private String nombre;
    private String detallePropuesta;
    private int idPropuesta;
    private int votos;
}
