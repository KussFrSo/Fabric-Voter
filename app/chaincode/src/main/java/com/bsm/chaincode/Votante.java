package com.bsm.chaincode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Votante { // Info basica de votante, el registro y su info personal va en la BBDD
    private String idVotante;
    private boolean votado;
    private int pesoVoto;
    private String delegarVotoTo;
    private String propuestaVotada;
}
