
package com.bsm.chaincode;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.List;
import java.util.Objects;

@DataType()
public final class AssetJamon {

    @Property()
    private final String id;

    @Property()
    private final String raza;

    @Property()
    private final String alimentacion;

    @Property()
    private final String denominacionOrigen;

    @Property()
    private final String propietario;

    @Property()
    private final String valor;

    @Property()
    private final List<String> intermediarios;


    public AssetJamon(@JsonProperty("id") final String id,
                      @JsonProperty("raza") final String raza,
                      @JsonProperty("alimentacion") final String alimentacion,
                      @JsonProperty("denominacionOrigen") final String denominacionOrigen,
                      @JsonProperty("propietario") final String propietario,
                      @JsonProperty("valor") final String valor,
                      @JsonProperty("intermediarios") final List<String> intermediarios) {
        this.id = id;
        this.raza = raza;
        this.alimentacion = alimentacion;
        this.denominacionOrigen = denominacionOrigen;
        this.propietario = propietario;
        this.valor = valor;
        this.intermediarios = intermediarios;
    }

    public String getId() {
        return id;
    }

    public String getRaza() {
        return raza;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public String getDenominacionOrigen() {
        return denominacionOrigen;
    }

    public String getPropietario() {
        return propietario;
    }

    public String getValor() {
        return valor;
    }

    public List<String> getIntermediarios() {
        return intermediarios;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        AssetJamon other = (AssetJamon) obj;

        return Objects.deepEquals(
                new String[] {getId(), getRaza(), getAlimentacion(), getDenominacionOrigen(), getPropietario(), getValor(), getIntermediarios().toString()},
                new String[] {other.getId(), other.getRaza(), other.getAlimentacion(), other.getDenominacionOrigen(), other.getPropietario(), other.getValor(), other.getIntermediarios().toString()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRaza(), getAlimentacion(), getDenominacionOrigen(), getPropietario(), getValor(), getIntermediarios().toString());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [id=" + id + ", raza="
                + raza + ", alimentacion=" + alimentacion + ", denominacionOrigen=" + denominacionOrigen + ", propietario=" + propietario + ", valor=" + valor + ", intermediarios=" + intermediarios + "]";
    }
}
