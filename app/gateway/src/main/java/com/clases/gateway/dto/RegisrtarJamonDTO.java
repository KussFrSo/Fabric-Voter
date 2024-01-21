package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisrtarJamonDTO {

    @ApiModelProperty(value = "Jamon ID", example = "1")
    String jamonId;

    @ApiModelProperty(value = "Raza", example = "Iberico")
    String raza;

    @ApiModelProperty(value = "Alimentacion", example = "Bellota")
    String alimentacion;

    @ApiModelProperty(value = "Denominacion de origen", example = "Dehesa de Extremadura")
    String denomOrig;

    @ApiModelProperty(value = "Owner", example = "Pepe")
    String owner;

    @ApiModelProperty(value = "Valor", example = "50")
    int valor;
}
