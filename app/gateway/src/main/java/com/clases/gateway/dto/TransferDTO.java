package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferDTO {


    @ApiModelProperty(value = "Asset ID", example = "1")
    String assetID;

    @ApiModelProperty(value = "New Owner", example = "Paco")
    String newOwner;
}
