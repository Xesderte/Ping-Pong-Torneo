package com.pingpong.torneo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class IndividuosRegistroDTO {
    
    @NotEmpty(message = "Debe enviar al menos un jugador")
    @Valid
    private List<JugadorDTO> jugadores;

    public List<JugadorDTO> getJugadores() { return jugadores; }
    public void setJugadores(List<JugadorDTO> jugadores) { this.jugadores = jugadores; }
}
