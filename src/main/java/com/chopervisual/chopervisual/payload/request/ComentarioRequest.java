package com.chopervisual.chopervisual.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComentarioRequest {

    @NotNull
    private Long helicopteroId;

    @NotBlank
    private String contenido;

    public Long getHelicopteroId() {
        return helicopteroId;
    }

    public void setHelicopteroId(Long helicopteroId) {
        this.helicopteroId = helicopteroId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}