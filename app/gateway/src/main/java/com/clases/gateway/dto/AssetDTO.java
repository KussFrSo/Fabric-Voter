package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssetDTO {

    @ApiModelProperty(value = "Asset ID", example = "1")
    String assetID;

    @ApiModelProperty(value = "Color", example = "Azul")
    String color;

    @ApiModelProperty(value = "Tamano", example = "10")
    int size;

    @ApiModelProperty(value = "Owner", example = "Pepe")
    String owner;

    @ApiModelProperty(value = "Valor", example = "50")
    int appraisedValue;
}
