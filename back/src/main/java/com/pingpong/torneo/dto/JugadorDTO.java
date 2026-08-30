package com.pingpong.torneo.dto;

import jakarta.validation.constraints.NotBlank;

public class JugadorDTO {
    @NotBlank(message = "El nombre del jugador es obligatorio")
    private String nombre;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
