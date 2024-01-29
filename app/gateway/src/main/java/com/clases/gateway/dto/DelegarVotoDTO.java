package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DelegarVotoDTO {

    @ApiModelProperty(value = "Votante Delegado ID", example = "1")
    String to;

    @ApiModelProperty(value = "Votacion ID", example = "1")
    String idVotacion;

    @ApiModelProperty(value = "Votante Emisor ID", example = "2")
    String idVotante;

}
