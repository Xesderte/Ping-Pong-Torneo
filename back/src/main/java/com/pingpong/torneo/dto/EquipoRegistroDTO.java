package com.pingpong.torneo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class EquipoRegistroDTO {
    
    private String nombreEquipo; // Opcional, puede ser auto-generado si es individual
    
    @NotEmpty(message = "Un equipo debe tener al menos un jugador")
    @Valid
    private List<JugadorDTO> jugadores;

    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
    public List<JugadorDTO> getJugadores() { return jugadores; }
    public void setJugadores(List<JugadorDTO> jugadores) { this.jugadores = jugadores; }
}
