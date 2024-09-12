package org.paulpucha.api.cuenta.bancaria.cuenta.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCuentaEnum {
    AHORROS("Ahorros"),
    CORRIENTE("Corriente");

    private final String value;

    TipoCuentaEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static org.paulpucha.api.cuenta.bancaria.cuenta.enumeration.TipoCuentaEnum fromString(String value) {
        for (org.paulpucha.api.cuenta.bancaria.cuenta.enumeration.TipoCuentaEnum tipo : org.paulpucha.api.cuenta.bancaria.cuenta.enumeration.TipoCuentaEnum.values()) {
            if (tipo.value.equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de cuenta inválido: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
