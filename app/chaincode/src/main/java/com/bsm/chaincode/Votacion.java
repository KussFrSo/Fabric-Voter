package com.bsm.chaincode;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import lombok.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@DataType()
public final class Votacion {

    @Property()
    private final String id;

    @Property()
    private final String nombre;

    @Property()
    private final Map<String, Votante> votantes;

    @Property()
    private final List<Propuesta> propuestas;

    @Property()
    private final EstadoVotacion estado;

    @Property()
    private final int totalVotantes;

    @Property()
    private final int votosEfectuados;

    @Property()
    private final int propuestaGanadora;

    @Property()
    private final long tiempoVotacion;

    @Property()
    private final long duracion;

    public Votacion(@JsonProperty("id") final String id,
                    @JsonProperty("nombre") final String nombre,
                    @JsonProperty("votantes") final Map<String, Votante> votantes,
                    @JsonProperty("propuestas") final List<Propuesta> propuestas,
                    @JsonProperty("estado") final EstadoVotacion estado,
                    @JsonProperty("totalVotantes") final int totalVotantes,
                    @JsonProperty("votosEfectuados") final int votosEfectuados,
                    @JsonProperty("propuestaGanadora") final int propuestaGanadora,
                    @JsonProperty("tiempoVotacion") final long tiempoVotacion,
                    @JsonProperty("duracion") final long duracion) {
        this.id = id;
        this.nombre = nombre;
        this.votantes = votantes;
        this.propuestas = propuestas;
        this.estado = estado;
        this.totalVotantes = totalVotantes;
        this.votosEfectuados = votosEfectuados;
        this.propuestaGanadora = propuestaGanadora;
        this.tiempoVotacion = tiempoVotacion;
        this.duracion = duracion;
    }
}

