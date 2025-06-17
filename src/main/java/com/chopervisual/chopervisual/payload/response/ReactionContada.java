package com.chopervisual.chopervisual.payload.response;

import com.chopervisual.chopervisual.models.EReaction;

public class ReactionContada {

    private EReaction descripcion;  // <-- tipo correcto
    private Long cantidad;

    public ReactionContada(EReaction descripcion, Long cantidad) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public EReaction getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(EReaction descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
