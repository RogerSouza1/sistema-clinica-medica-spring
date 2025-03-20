package com.orange.sistema_clinca_medica_web.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CargoNome {
    ROLE_PACIENTE,
    ROLE_MEDICO,
    ROLE_USUARIO_ADMINISTRADOR,
    ROLE_USUARIO_PADRAO;

    @JsonCreator
    public static CargoNome fromString(String value) {
        return CargoNome.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }

    public String toUpperCase() {
        return this.name().toUpperCase();
    }
}
