package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferJamonDTO {


    @ApiModelProperty(value = "Jamon ID", example = "1")
    String jamonId;

    @ApiModelProperty(value = "Nuevo owner", example = "Paco")
    String newOwner;

    @ApiModelProperty(value = "Nuevo valor", example = "80")
    int newValue;

}
