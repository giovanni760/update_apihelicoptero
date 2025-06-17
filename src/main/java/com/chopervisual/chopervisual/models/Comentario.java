package com.chopervisual.chopervisual.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String contenido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "helicoptero_id", nullable = false)
    private Helicoptero helicoptero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    public Comentario() {
    }

    public Comentario(String contenido, Helicoptero helicoptero, User usuario) {
        this.contenido = contenido;
        this.helicoptero = helicoptero;
        this.usuario = usuario;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Helicoptero getHelicoptero() {
        return helicoptero;
    }

    public void setHelicoptero(Helicoptero helicoptero) {
        this.helicoptero = helicoptero;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}
