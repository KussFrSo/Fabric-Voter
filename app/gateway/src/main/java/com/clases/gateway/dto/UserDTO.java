package com.clases.gateway.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDTO {
    @ApiModelProperty(value = "Nombre usuario", example = "jairo")
    String name;

    @ApiModelProperty(value = "DNI usuario", example = "12345678A")
    String dni;

    @ApiModelProperty(value = "Email usuario", example = "jairo@hotmail.com")
    String email;

    @ApiModelProperty(value = "Ha donado", example = "true")
    Boolean hasDonated;

    @ApiModelProperty(value = "Contraseña", example = "12345aA")
    String password;

    @ApiModelProperty(value = "Ledger Id", example = "XXXXXX")
    String ledgerId;
}
