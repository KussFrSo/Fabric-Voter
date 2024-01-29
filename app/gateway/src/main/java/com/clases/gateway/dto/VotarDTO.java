package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VotarDTO {


    @ApiModelProperty(value = "Votacion ID", example = "1")
    String idVotacion;

    @ApiModelProperty(value = "Propuesta ID", example = "2")
    String idPropuesta;

    @ApiModelProperty(value = "Votante ID", example = "2")
    String idVotante;

}
