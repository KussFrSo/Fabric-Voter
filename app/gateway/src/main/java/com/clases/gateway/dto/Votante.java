package com.clases.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Votante { // Info basica de votante, el registro y su info personal va en la BBDD
    private String idVotante;
    private boolean votado;
    private int pesoVoto;
    private String delegarVotoTo;
    private String propuestaVotada;
}
