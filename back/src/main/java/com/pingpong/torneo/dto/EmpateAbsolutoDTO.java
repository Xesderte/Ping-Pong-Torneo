package com.pingpong.torneo.dto;

import jakarta.validation.constraints.NotNull;

public class EmpateAbsolutoDTO {
    
    @NotNull(message = "Debe proporcionar el ID del equipo ganador")
    private Long idEquipoGanador;

    public Long getIdEquipoGanador() { return idEquipoGanador; }
    public void setIdEquipoGanador(Long idEquipoGanador) { this.idEquipoGanador = idEquipoGanador; }
}
