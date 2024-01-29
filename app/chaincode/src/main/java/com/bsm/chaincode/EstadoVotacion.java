package com.bsm.chaincode;

import lombok.Getter;

@Getter
public enum EstadoVotacion {
    PREPARACION("Preparación"),
    ABIERTA("Abierta"),
    FINALIZADA("Finalizada");

    private final String estado;

    EstadoVotacion(String estado) {
        this.estado = estado;
    }
}
