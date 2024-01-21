package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseDTO {

    @ApiModelProperty(value = "Codigo respuesta", example = "0")
    String code;

    @ApiModelProperty(value = "Respuesta", example = "Asset Creado")
    String data;
}
