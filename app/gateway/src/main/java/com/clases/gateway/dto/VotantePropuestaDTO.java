package com.clases.gateway.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VotantePropuestaDTO {
    @ApiModelProperty(value = "Nombre usuario", example = "jairo")
    String name;

    @ApiModelProperty(value = "DNI usuario", example = "12345678A")
    String dni;

    @ApiModelProperty(value = "Email usuario", example = "jairo@hotmail.com")
    String email;
}
