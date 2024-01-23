package com.clases.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RegistrarVotacionDTO {

    @ApiModelProperty(value = "Votacion ID", example = "1")
    String id;

    @ApiModelProperty(value = "Mapa de votantes",
            example = "{'votante1': {'idVotante': '1', 'votado': false, 'pesoVoto': 1, 'delegarVotoTo': '', 'propuestaVotada': ''}, 'votante2': {'idVotante': '2', 'votado': true, 'pesoVoto': 2, 'delegarVotoTo': '1', 'propuestaVotada': '2'}}")
    private Map<String, Votante> votantes;

    @ApiModelProperty(value = "Lista de propuestas",
            example = "[{'nombre': 'Propuesta1', 'detallePropuesta': 'Detalle1', 'idPropuesta': 1, 'votos': 10}, {'nombre': 'Propuesta2', 'detallePropuesta': 'Detalle2', 'idPropuesta': 2, 'votos': 5}]")
    private List<Propuesta> propuestas;

    @ApiModelProperty(value = "El estado actual de la votación",
            example = "ABIERTA")
    private EstadoVotacion estadoVotacion;

    @ApiModelProperty(value = "Total votantes", example = "5")
    int totalVotantes;

    @ApiModelProperty(value = "Votos efectuados", example = "5")
    int votosEfectuados;

    @ApiModelProperty(value = "Propuesta ganadora", example = "2")
    int propuestaGanadora;

    @ApiModelProperty(value = "Tiempo votacion", example = "Pepe")
    int tiempoVotacion;

    @ApiModelProperty(value = "Duracion", example = "60")
    int duracion;
}
